import java.util.*;

/*===============================================================*
 *  File: SWP.java                                               *
 *                                                               *
 *  This class implements the sliding window protocol            *
 *  Used by VMach class					         *
 *  Uses the following classes: SWE, Packet, PFrame, PEvent,     *
 *                                                               *
 *  Author: Professor SUN Chengzheng                             *
 *          School of Computer Engineering                       *
 *          Nanyang Technological University                     *
 *          Singapore 639798                                     *
 *===============================================================*/

public class SWP {

    /*========================================================================*
     the following are provided, do not change them!!
     *========================================================================*/
    //the following are protocol constants.
    public static final int MAX_SEQ = 7;
    public static final int NR_BUFS = (MAX_SEQ + 1) / 2;

    // the following are protocol variables
    private int oldest_frame = 0;
    private PEvent event = new PEvent();
    private Packet out_buf[] = new Packet[NR_BUFS];

    //the following are used for simulation purpose only
    private SWE swe = null;
    private String sid = null;

    //Constructor
    public SWP(SWE sw, String s) {
        swe = sw;
        sid = s;
    }

    //the following methods are all protocol related
    private void init() {
        for (int i = 0; i < NR_BUFS; i++) {
            out_buf[i] = new Packet();
        }
    }

    private void wait_for_event(PEvent e) {
        swe.wait_for_event(e); //may be blocked
        oldest_frame = e.seq;  //set timeout frame seq
    }

    private void enable_network_layer(int nr_of_bufs) {
        //network layer is permitted to send if credit is available
        swe.grant_credit(nr_of_bufs);
    }

    private void from_network_layer(Packet p) {
        swe.from_network_layer(p);
    }

    private void to_network_layer(Packet packet) {
        swe.to_network_layer(packet);
    }

    private void to_physical_layer(PFrame fm) {
        System.out.println("SWP: Sending frame: seq = " + fm.seq +
                " ack = " + fm.ack + " kind = " +
                PFrame.KIND[fm.kind] + " info = " + fm.info.data);
        System.out.flush();
        swe.to_physical_layer(fm);
    }

    private void from_physical_layer(PFrame fm) {
        PFrame fm1 = swe.from_physical_layer();
        fm.kind = fm1.kind;
        fm.seq = fm1.seq;
        fm.ack = fm1.ack;
        fm.info = fm1.info;
    }


    /*===========================================================================*
         implement your Protocol Variables and Methods below:
     *==========================================================================*/

    private Timer timer[] = new Timer[NR_BUFS]; // Frame timers
    private Timer ack_timer = new Timer();  // Ack timer
    private boolean no_nak = true;   // No nak has been sent yet

    //  Same as between in protocol5, but shorter and more obscure.
    public boolean between(int a, int b, int c) {
        return ((a <= b) && (b < c)) || ((c < a) && (a <= b)) || ((b < c) && (c < a));
    }

    // Construct and send a data, ack and nak frame
    public void send_frame(int fk, int frame_nr, int frame_expected, Packet buffer[]) {
        PFrame s = new PFrame();

        s.kind = fk;
        if (fk == PFrame.DATA) {
            s.info = buffer[frame_nr % NR_BUFS];
        }
        s.seq = frame_nr;
        s.ack = (frame_expected + MAX_SEQ) % (MAX_SEQ + 1);
        if (fk == PFrame.NAK) {
            no_nak = false;
        }
        to_physical_layer(s);
        if (fk == PFrame.DATA) {
            start_timer(frame_nr);
        }
        stop_ack_timer();
    }

    public void protocol6() {
        PFrame r = new PFrame();    //  Scratch var

        int ack_expected = 0;        // lower edge of sender's window
        int next_frame_to_send = 0;    // upper edge of sender's window + 1
        int frame_expected = 0;        // lower edge of receiver's window
        int too_far = NR_BUFS;        // upper edge of receiver's window + 1

        Packet in_buf[] = new Packet[NR_BUFS];  //  Buffer for incoming packets
        boolean arrived[] = new boolean[NR_BUFS];   //  Array of arrived values for packets
        for (int i = 0; i < NR_BUFS; i++) {
            arrived[i] = false;
            in_buf[i] = new Packet();
        }

        enable_network_layer(NR_BUFS);
        init();

        while (true) {
            wait_for_event(event);  //  Waiting to recive new event
            switch (event.type) {
                //  Case network layer ready, fetch the packet, transmit the frame and increase nextframetosend
                case (PEvent.NETWORK_LAYER_READY):
                    from_network_layer(out_buf[next_frame_to_send % NR_BUFS]);
                    send_frame(PFrame.DATA, next_frame_to_send, frame_expected, out_buf);
                    next_frame_to_send = incFrameNr(next_frame_to_send);
                    break;

                //  Case frame arrival, fetch frame from physical layer
                case (PEvent.FRAME_ARRIVAL):
                    from_physical_layer(r);
                    //  If it's data, undamaged data has arrived
                    if (r.kind == PFrame.DATA) {
                        //  If its the wrong frame and we haven't sent a nak
                        if ((r.seq != frame_expected) && no_nak) {
                            send_frame(PFrame.NAK, 0, frame_expected, out_buf);
                        } else {
                            start_ack_timer();
                        }
                        //  If frame is needed
                        if (between(frame_expected, r.seq, too_far) && (arrived[r.seq % NR_BUFS] == false)) {
                            arrived[r.seq % NR_BUFS] = true;
                            in_buf[r.seq % NR_BUFS] = r.info;
                            while (arrived[frame_expected % NR_BUFS]) {
                                //  Pass frame and increase window
                                to_network_layer(in_buf[frame_expected % NR_BUFS]);
                                no_nak = true;
                                arrived[frame_expected % NR_BUFS] = false;

                                frame_expected = incFrameNr(frame_expected);
                                too_far = incFrameNr(too_far);
                                start_ack_timer();
                            }
                        }
                    }
                    //  If it's a NAK, and it's a needed frame
                    if ((r.kind == PFrame.NAK) && between(ack_expected, (r.ack + 1) % (MAX_SEQ + 1), next_frame_to_send)) {
                        send_frame(PFrame.DATA, (r.ack + 1) % (MAX_SEQ + 1), frame_expected, out_buf);
                    }
                    //  If frame needed, stop timer and move window
                    while (between(ack_expected, r.ack, next_frame_to_send)) {
                        stop_timer(ack_expected);
                        ack_expected = incFrameNr(ack_expected);
                        enable_network_layer(1);
                    }
                    break;
                //  Case damaged frame
                case (PEvent.CKSUM_ERR):
                    if (no_nak) {
                        send_frame(PFrame.NAK, 0, frame_expected, out_buf);
                    }
                    break;
                //  Case timeout
                case (PEvent.TIMEOUT):
                    if (between(ack_expected, oldest_frame, next_frame_to_send)) {
                        send_frame(PFrame.DATA, oldest_frame, frame_expected, out_buf);
                    }
                    break;
                //  Case ackout
                case (PEvent.ACK_TIMEOUT):
                    send_frame(PFrame.ACK, 0, frame_expected, out_buf);
                    break;
                //  Case default (No other hits)
                default:
                    System.out.println("SWP: undefined event type = " + event.type);
                    System.out.flush();
            }
        }
    }

    //  Increases frame_nr by 1, when higher than MAX_SQ, set to 0
    public int incFrameNr(int fnr) {
        fnr++;
        if (fnr > MAX_SEQ) {
            fnr = 0;
        }
        return fnr;
    }

    /* Note: when start_timer() and stop_timer() are called,
    the "seq" parameter must be the sequence number, rather
    than the index of the timer array,
    of the frame associated with this timer,
   */

    //  Class for Frame timers
    public class FrameTimerTask extends TimerTask {
        int seq;

        public FrameTimerTask(int seq) {
            this.seq = seq;
        }

        public void run() {
            swe.generate_timeout_event(seq);
        }
    }

    //  Class for Ack timers
    public class AckTimerTask extends TimerTask {
        public void run() {
            swe.generate_acktimeout_event();
        }
    }

    //  Start timer
    private void start_timer(int seq) {
        stop_timer(seq);
        timer[seq % NR_BUFS] = new Timer();
        timer[seq % NR_BUFS].schedule(new FrameTimerTask(seq), 200);
    }

    //  Stop timer
    public void stop_timer(int seq) {
        if (timer[seq % NR_BUFS] != null) {
            timer[seq % NR_BUFS].cancel();
            timer[seq % NR_BUFS] = null;
        }
    }

    //  Start acktimer
    public void start_ack_timer() {
        stop_ack_timer();
        ack_timer = new Timer();
        ack_timer.schedule(new AckTimerTask(), 100);
    }

    //  Stop acktimer
    public void stop_ack_timer() {
        if (ack_timer != null) {
            ack_timer.cancel();
            ack_timer = null;
        }
    }
}//End of class

/* Note: In class SWE, the following two public methods are available:
   . generate_acktimeout_event() and
   . generate_timeout_event(seqnr).

   To call these two methods (for implementing timers),
   the "swe" object should be referred as follows:
     swe.generate_acktimeout_event(), or
     swe.generate_timeout_event(seqnr).
*/
import java.util.*;

class TraadSort implements Runnable {

	boolean ikkeferdig = false;

	public void run() {
String[] usortert = Oblig9.getMonitor().hentUsortertArray();

String[] sortert = sort(usortert);
Oblig9.getMonitor().taImotSortert(sortert);



		while(!ikkeferdig) {
			String [] uflettet1 = Oblig9.getMonitor().hentSortertArray();
			String [] uflettet2 = Oblig9.getMonitor().hentSortertArray();
			if(uflettet1 == null || uflettet2 == null) {
				Oblig9.getMonitor().taImotSortert(null);
			}
			else{
				List<String> flettet = flett(Arrays.asList(uflettet1),Arrays.asList(uflettet2));
				String[] flettetArray = flettet.toArray(new String[flettet.size()] );
				Oblig9.getMonitor().taImotSortert(flettetArray);
			}

		}
	}
	public static String[] sort(String[] usortert) {
			Arrays.sort(usortert);
			/*String tempStr = "";
			String[] sortert = usortert;
		    for (int t = 0; t < sortert.length - 1; t++) {
            for (int i= 0; i < sortert.length - t -1; i++) {
                if(sortert[i+1].compareTo(sortert[i])<0) {
                    tempStr = sortert[i];
                    sortert[i] = sortert[i + 1];
                    sortert[i + 1] = tempStr;
                }
            }
          }*/
		return usortert;
	}
	public List<String> flett(List<String> venstre, List<String> hoyre) {
            List<String> result = new ArrayList<String>(venstre.size() + hoyre.size());

            Iterator<String> venstreIterator = venstre.iterator();
            Iterator<String> hoyreIterator = hoyre.iterator();

            String fromvenstre = null;
            String fromhoyre = null;

            while (venstreIterator.hasNext() || hoyreIterator.hasNext()) {

                fromvenstre = fromvenstre == null ? venstreIterator.hasNext() ? venstreIterator.next() : null : fromvenstre;
                fromhoyre = fromhoyre == null ? hoyreIterator.hasNext() ? hoyreIterator.next() : null : fromhoyre;

                if (fromvenstre != null && (fromhoyre == null || fromvenstre.compareTo(fromhoyre) < 0)) {
                    result.add(fromvenstre);
                    fromvenstre = null;
                } else if (fromhoyre != null && (fromvenstre == null || fromhoyre.compareTo(fromvenstre) < 0)) {
                    result.add(fromhoyre);
                    fromhoyre = null;
                }
            }

            return result;
        }
    }

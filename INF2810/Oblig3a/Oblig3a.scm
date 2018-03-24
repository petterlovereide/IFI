(load "prekode3a.scm")

;;1a
(define (list-to-stream list)
  (define (make-stream init list)
    (if (= 1 (length list))
        (cons-stream (car list) init)
        (make-stream (cons-stream (car list) init) (cdr list))))
  (make-stream the-empty-stream (reverse list)))

(define stream1 (list-to-stream (list 1 2 3 4 5)))
(define stream2 (list-to-stream (list 1 2 2 1)))
;;(show-stream stream1 5)

(define (stream-to-list stream)
  (define (make-list init stream)
    (if (null? stream)
        init
        (reverse (make-list (cons (stream-car stream) init) (stream-cdr stream)))))
    (make-list '() stream))

;;(stream-to-list stream1)

;;1b

(define (streams-to-lists streams)
  (define (make-lists init streams)
    (if (null? streams)
        init
        (make-lists (cons (stream-to-list (car streams)) init) (cdr streams))))
  (make-lists '() streams))

;;(apply append (streams-to-lists (list stream1 stream2)))

(define (check-streams-null streams)
  (cond
    ((null? streams) #f)
    ((stream-null? (car streams)) #t)
    (else (check-streams-null (cdr streams)))))

(define (stream-map proc . argstreams)
  (if (check-streams-null argstreams)
      the-empty-stream
      (cons-stream
       (apply proc (map stream-car argstreams))
       (apply stream-map
              (cons proc (map stream-cdr argstreams))))))


;;(show-stream (stream-map + stream1 stream2) 10)

;;2abcd
;;definerer et ord "objekt" som har en teller og verdi
(define (ord verdi teller)
  (define (tell)
    (set! teller (+ 1 teller)))
  (define (get-teller)
    teller)
  (define (get-verdi)
    verdi)
  (define (menu mess)
    (cond
      ((eq? mess 'tell)(tell))
      ((eq? mess 'get-teller)(get-teller))
      ((eq? mess 'get-verdi)(get-verdi))))
  menu)

;;definerer et ordpar som har to ord og en teller
(define (ordpar string1 string2 teller)
  (define (tell)
     (begin
       (string1 'tell)
       (set! teller (+ 1 teller))))
  (define (set-teller int)
    (set! teller (car int)))
  (define (get-teller)
    teller)
  (define (get-string1)
    (string1 'get-verdi))
  (define (get-string1-teller)
    (string1 'get-teller))
  (define (string1-tell)
    (string1 'tell))
  (define (get-string2)
    (string2 'get-verdi))
  (define (menu mess . arg)
    (cond
      ((eq? mess 'tell)(tell))
      ((eq? mess 'get-teller)(get-teller))
      ((eq? mess 'get-string1)(get-string1))
      ((eq? mess 'get-string1-teller)(get-string1-teller))
      ((eq? mess 'string1-tell)(string1-tell))
      ((eq? mess 'get-string2)(get-string2))
      ((eq? mess 'set-teller)(set-teller arg))))
  menu)

;; en lm med en liste som inneholder ordpar
(define (lm ordpar-liste)
  (define (lookup liste string1 string2 tell)
    (if (null? liste)
        0
        (if (string=? string1 ((car liste) 'get-string1))
             (if (string=? string2 ((car liste) 'get-string2))
                 (if tell
                     ((car liste) 'tell)
                     ((car liste) 'get-teller))
                 (begin
                   (if tell
                       ((car liste) 'string1-tell))
                   (lookup (cdr liste) string1 string2 tell)))
            (lookup (cdr liste) string1 string2 tell))))
  (define (record liste string1 string2)
    (if (< 0 (lookup liste string1 string2 #f))
        (lookup liste string1 string2 #t)
        (set! ordpar-liste (cons (ordpar (ord string1 1) (ord string2 1) 1) ordpar-liste))))
  (define (estimate init ordliste)
    (if (null? ordliste)
        init
        (begin
          ((car ordliste) 'set-teller (/ ((car ordliste) 'get-teller) ((car ordliste) 'get-string1-teller)))
          (estimate (cons (car ordliste) init) (cdr ordliste)))))
  (define (menu mess string1 string2)
    (cond
      ((eq? mess 'lookup)(lookup ordpar-liste string1 string2 #f))
      ((eq? mess 'record)(record ordpar-liste string1 string2))
      ((eq? mess 'estimate)(estimate '() ordpar-liste))))
  menu)

;;prosedyrer
(define (make-lm)(lm '()))
(define (lm-lookup-biagram lm string1 string2)(lm 'lookup string1 string2))
(define (lm-record-biagram! lm string1 string2)(lm 'record string1 string2))
(define (lm-estimate lm-in)(lm (lm-in 'estimate "" "")))


;;lm-train som gaar gjennom en fil og returnerer en lm
(define (lm-train! filnavn)

  (begin
    (define lmf (make-lm))
    (define (en-setning setning)
      (if (> (length setning) 1)
          (begin
            (lm-record-biagram! lmf (car setning) (cadr setning))
            (en-setning (cdr setning)))))
    (define (en-tekst tekst)
      (if (null? tekst)
          lmf
          (begin
            (en-setning (car tekst))
            (en-tekst (cdr tekst)))))
    (en-tekst (read-corpus filnavn))))

(define lm1 (lm-train! "test2.txt"))
;;(lm-lookup-biagram lm1 "the" "case")

(define lm2 (lm-estimate lm1))
;;(lm-lookup-biagram lm2 "the" "case")


;;lm-score. returnerer en tallverdi. tar inn liste med ord
(define (lm-score lm sentence)
  (define (et-ordpar init ordliste)
    (if (< (length ordliste) 2)
        init
        (et-ordpar (* (lm-lookup-biagram lm (car ordliste) (cadr ordliste)) init) (cdr ordliste))))

  (et-ordpar 1 sentence))

;;(lm-score lm2 (list  "<s>" "As" "the" "court" "approached" "the" "case," "it" "dismissed" "unfair," "fundamentally" "illegal" "evidence." "</s>"))

;;test alle setningene i en fil
(define (score-corpus lm filnavn)

  (define (score-en lm init liste)
    (if (null? liste)
        init
        (score-en lm (cons (lm-score lm (car liste)) init) (cdr liste))))

  (score-en lm '() (read-corpus filnavn)))

;;den forste setningen er den mest sannsynlige
;;(score-corpus lm2 "test.txt")



  

  



 








      
  
  
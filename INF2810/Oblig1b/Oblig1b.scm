;;1f
;;(cadr '(0 42 #t bar))

;;1g
;;(cadar '((0 42) (#t bar)))

;;1h
;;(caadr'((0) (42 #t) (bar)))

;;1i
;;(list (list 0 42) (list #t 'bar))

;;2a
(define (length2 items)
  (define (teller liste i)
    (if (null? liste)
        i
        (teller (cdr liste) (+ 1 i))))
  (teller items 0))
;;(length2 (list 1 2 3 4 7))

;;2b funker med tungvint
(define (reduce-reverse proc init items)
  (define (reverse-liste liste)
    (if (null? liste)
        '()
        (append (reverse-liste (cdr liste)) (list (car liste)))))

  (if (null? items)
      init
      (proc (car (reverse-liste items)) (reduce-reverse proc init (reverse-liste (cdr (reverse-liste items)))))))
;;(reduce-reverse + 0 '(1 2 3 4))

;;2c
(define (all? pred liste)
   (if (null? liste)
      #t
      (if (pred (car liste))
          (all? pred (cdr liste))
          #f)))
;;(all? even? '(2 4 8))

;;2d
(define (nth i liste)
  (if (= i 0)
      (car liste)
      (nth (- i 1) (cdr liste))))
;;(nth 0 '(47 11 12 13))


;;2e
(define (where nr items)
  (define (teller liste i)
    (if (= (car liste) nr)
        i
        (teller (cdr liste) (+ i 1))))
  (teller items 0))
;;(where 3 '(1 2 3 3 4 5 3))

;;2f
(define (map2 proc liste1 liste2)
  (define (merge-first init proc liste1 liste2)
    (if (null? liste1)
        init
        (if (null? liste2)
            init
            (merge-first (append init (list(proc (car liste1) (car liste2)))) proc (cdr liste1) (cdr liste2)))))
  (merge-first '() proc liste1 liste2))
;;(map2 + '(1 2 3 4) '(5 6 7))

;;2g
(define (gjen liste1 liste2)
  (define (gjennomsnitt)
    (lambda (x y) (/ (+ x y) 2)))
  (define (merge-first init liste1 liste2)
    (if (null? liste1)
        init
        (if (null? liste2)
            init
            (merge-first (append init (list((gjennomsnitt) (car liste1) (car liste2)))) (cdr liste1) (cdr liste2)))))
  (merge-first '() liste1 liste2))
;;(gjen '(1 2 3 4) '(3 4 5))

;;2h
(define (both? proc)
  (lambda (x y)
    (if (proc x)
        (if (proc y)
            #t
            #f)
        #f)))

;;(map2 (both? even?) '(1 2 3) '(3 4 5)) 

;;2i
(define (self proc)
  (lambda (x) (proc x x)))
;;((self +) 5)





      
  

  
  
      
      
  


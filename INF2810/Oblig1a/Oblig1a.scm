;2b med if
(define (sign1 x)
  (if (> x 0) 1)
  (if (< x 0) -1)
  (if (= x 0) 0))

;2b med cond
(define (sign2 x)
  (cond
    ((positive? x) 1)
    ((negative? x) -1)
    ((zero? x) 0)))

;2c
(define (sign3 x)
  (or
   (and (positive? x) 1)
   (and (negative? x) -1)
   (and (zero? x) 0)))

;3a
(define (add1 x)
  (+ x 1))

(define (sub1 x)
  (- x 1))

;3b
(define (plus x y)
  (if (zero? y) x)
  (if (positive? y) (plus (add1 x) (sub1 y))))

;3d. Vi kan fjerne b og n fra power-iter definisjonen
(define (power-close-to b n)
  (define (power-iter e)
    (if (> (expt b e) n)
      e
      (power-iter (+ 1 e))))
  (power-iter 1))










  

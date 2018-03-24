(load "prekode2b.scm")

;; 1a
(define (make-counter)
  (let ((teller 0))
    (lambda ()
      (begin
        (set! teller (+ teller 1))
        teller))))

(define count 42)
(define c1 (make-counter))
(define c2 (make-counter))

;;(c1)
;;(c1)


;;2a
(define  (make-stack liste)
  (define (pop!)
    (if (null? liste)
        'tom_stack
         (set! liste (reverse (cdr (reverse liste))))))
  (define (push! items)
    (set! liste (append liste items)))
  (define (stack)
    liste)

  (define (controller message args)
    (cond
      ((eq? message 'pop!)(pop!))
      ((eq? message 'stack)(stack))
      ((eq? message 'push!)(push! args))))
  controller)

(define s1 (make-stack (list 'foo 'bar)))
(define s2 (make-stack '()))

(define (pop! var)
  (var 'pop! '()))
(define (push! var . args)
  (var 'push! args))
(define (stack var)
  (var 'stack '()))

;;(pop! s1)
;;(stack s1)
;;(push! s1 'foo 'faa)
;;(stack s1)


;; 4ab
(define (mem meld proc) 
  (define (memoize table proc)
    (lambda x
          (if (eq? #f (lookup x table))
             (begin
               (insert! x (apply proc x) table)
               (lookup x table))
             (lookup x table))))

  (define (unmemoize proc)
    (lambda x
           (apply proc x)))

  (define (start meld proc)
    (cond
      ((eq? meld 'memoize) (memoize (make-table) proc))
      ((eq? meld 'unmemoize) (unmemoize proc))))
  (start meld proc))

;;(set! fib (mem 'memoize fib))
;;(fib 3)
;;(fib 3)
;;(fib 2)
;;(fib 4)

;;(set! test-proc (mem 'memoize test-proc))
;;(test-proc 40 41 42 43 44)
;;(test-proc 40 41 42 43 44)
;;(test-proc 42 43 44)

;;(set! fib (mem 'unmemoize fib))
;;(fib 3)
;;(fib 3)










  
          






   




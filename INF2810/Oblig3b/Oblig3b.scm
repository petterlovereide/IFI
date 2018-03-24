(load "evaluator.scm")


(set! the-global-environment (setup-environment))
(define glob the-global-environment)
(define repl read-eval-print-loop)

;;1a

;; Den første cond vil bli tolket som Condition, mens den andre som en variabel. Det samme med else. Det er pga syntaksen

;; (foo 2 square) vil returnere 0, fordi cond har verdi 2 og dermed er (= cond 2) true, og det returneres 0.

;; (foo 4 square) vil returnere 16, fordi else slårr til, og dermed får vi (else cond), som blir (square 4), som blir (* 4 4)

;;(cond ((= cond 2) 0)   Her får vi 2, fordi cond-argumentet er 3, og else-argumentet er (/ x 2). Dermed slår else til.
;;     (else (else 4)))  Når else slår til, blir det (/ 4 2), som er 2

(repl)

;;(mc-eval '(install-primitive! 'square (lambda (x) (* x x))) the-global-environment)

;;(mc-eval '(square 4) the-global-environment)

;;the-global-environment
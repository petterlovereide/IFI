
;;; "Metacircular evaluator", basert på koden i seksjon 4.1.1-4.1.4 i SICP.
;;; Del av innlevering 3b i INF2810, vår 2017.
;; 
;; Last hele filen inn i Scheme. For å starte read-eval-print loopen og 
;; initialisere den globale omgivelsen, kjør:
;; (set! the-global-environment (setup-environment))
;; (read-eval-print-loop)
;;
;; Merk at det visse steder i koden, som i `special-form?', vanligvis
;; ville være mere naturlig å bruke `or' enn `cond'. Evaluatoren er
;; skrevet helt uten bruk av `and' / `or' for å vise at disse likevel
;; kan støttes i det implementerte språket selv om de ikke brukes i
;; implementeringsspråket. (Se oppgave 3a for mer om dette.)

;; hack for å etterlikne SICPs feilmeldinger:
(define exit-to-toplevel 'dummy)
(call-with-current-continuation 
 (lambda (cont) (set! exit-to-toplevel cont)))

(define (error reason . args)
  (display "ERROR: ")
  (display reason)
  (for-each (lambda (arg) 
	      (display " ")
	      (write arg))
	    args)
  (newline)
  (exit-to-toplevel))


;;; Selve kjernen i evaluatoren (seksjon 4.1.1, SICP):
;;; -----------------------------------------------------------------------

;; Merk at vi skiller ut evaluering av special forms i en egen prosedyre.

(define (mc-eval exp env) ;; tilsvarer eval i SICP
  (cond ((self-evaluating? exp) exp)
        ((variable? exp)(lookup-variable-value exp env))
        ((special-form? exp)(eval-special-form exp env))
        ((application? exp)
         (mc-apply (mc-eval (operator exp) env)
                   (list-of-values (operands exp) env)))
        (else
         (error "Unknown expression type -- mc-eval:" exp))))

(define (mc-apply proc args) ;; tilsvarer apply i SICP
  (cond ((primitive-procedure? proc)
         (apply-primitive-procedure proc args))
        ((compound-procedure? proc)
         (eval-sequence
          (procedure-body proc)
          (extend-environment
           (procedure-parameters proc)
           args
           (procedure-environment proc))))
        (else
         (error
          "Unknown procedure type -- mc-apply:" proc))))

(define (eval-special-form exp env)
  (cond ((quoted? exp) (text-of-quotation exp))
        ((assignment? exp) (eval-assignment exp env))
        ((definition? exp) (eval-definition exp env))
        ((if? exp) (eval-if exp env))
        ((lambda? exp)
         (make-procedure (lambda-parameters exp)
                         (lambda-body exp)
                         env))
        ((begin? exp) 
         (eval-sequence (begin-actions exp) env))
        ((cond? exp) (mc-eval (cond->if exp) env))
        ((install-primitive? exp) (install-primitive! exp env))
        ((and? exp) (mc-and exp env))
        ((let? exp) (mc-let exp env))
        ((let2? exp) (mc-let2 exp env))
        ((or? exp) (mc-or exp env))))

(define (special-form? exp)
  (cond ((quoted? exp) #t)
        ((assignment? exp) #t)
        ((definition? exp) #t)
        ((if? exp) #t)
        ((lambda? exp) #t)
        ((begin? exp) #t)
        ((cond? exp) #t)
        ((install-primitive? exp) #t)
        ((and? exp) #t)
        ((or? exp) #t)
        ((let? exp) #t)
        ((let2? exp) #t)
        (else #f)))

;:2b install primitive
(define (install-primitive? exp)
  (tagged-list? exp 'install-primitive!))

(define (install-primitive! exp env)
  (begin
    (set! primitive-procedures (cons (list (primitive-implementation exp) (caddr exp)) primitive-procedures))
    (set! the-global-environment (extend-environment (primitive-procedure-names) (primitive-procedure-objects) the-empty-environment))
    'primitive-installed))

;;Funker ikke!
;;Legger til nye funksjon i primitive listen, og deretter opdaterer the-glabal-environment.

;;2b slutt
  
;;3a and og or
(define (and? exp)
  (tagged-list? exp 'and))

(define (or? exp)
  (tagged-list? exp 'or))

(define (mc-and exp env)
  (cond
    ((= 1 (length exp)) #t)
    ((= 2 (length exp)) (mc-eval (cadr exp) env))
    ((< 2 (length exp))
     (if (mc-eval (cadr exp) env)
         (mc-and (cdr exp) env)
         #f))))

(define (mc-or exp env)
  (cond
    ((= 1 (length exp)) #t)
    ((= 2 (length exp)) (mc-eval (cadr exp) env))
    ((< 2 (length exp))
     (if (mc-eval (cadr exp) env)
         #t
         (mc-or (cdr exp) env)))))
;;3a ferdig

;;3b if
(define (eval-if exp env)
  (cond
    ((eq? (car exp) 'if)
     (if (mc-eval (cadr exp) env)
         (mc-eval (cadddr exp) env)
         (eval-if (cddddr exp) env)))
    ((eq? (car exp) 'elseif)
     (if (mc-eval (cadr exp) env)
         (mc-eval (cadddr exp) env)
         (eval-if (cddddr exp) env)))
    ((eq? (car exp) 'else)
     (mc-eval (cadr exp) env))))
;;3b slutt


;;3c let
(define (let? exp)
  (tagged-list? exp 'let))

(define (mc-let exp env)
  (mc-eval (let-run (let-getvars exp) (let-getvals exp) (let-getbody exp)) env))

;;Gjor om fra let til lesbar funksjon
(define (let-run vars vals body)
  (define (var-run var val body init)
    (cond
      ((null? body) init)
      ((pair? (car body))
       (var-run var val (cdr body) (cons (var-run var val (car body) '()) init)))
      ((eq? var (car body))
       (var-run var val (cdr body) (cons val init)))
      (else (var-run var val (cdr body) (cons (car body) init)))))

  (define (body-run vars vals body)
    (if (null? vars)
        body
        (body-run (cdr vars) (cdr vals) (var-run (car vars) (car vals) body '()))))
  (body-run vars vals body))
                 
;;Henter variabllene
(define (let-getvars exp)
  (define (run vars init)
    (if (null? vars)
        (reverse init)
        (run (cdr vars) (cons (caar vars) init))))
  (run (cadr exp) '()))

;;Henter values
(define (let-getvals exp)
  (define (run vals init)
    (if (null? vals)
        (reverse init)
        (run (cdr vals) (cons (cadar vals) init))))
  (run (cadr exp) '()))

;;Henter body
(define (let-getbody exp)
  (caddr exp))     
;;3c slutt

;;3d let2
(define (let2? exp)
  (tagged-list? exp 'let2))

(define (mc-let2 exp env)
  (mc-eval (let-run (let-getvars2 exp) (let-getvals2 exp) (let-getbody2 exp)) env))

(define (let-getvars2 exp)
  (define (run exp init)
    (cond
      ((eq? (cadddr exp) 'and)
       (run (cddddr exp) (cons (car exp) init)))
      ((eq? (cadddr exp) 'in)
       (reverse (cons (car exp) init)))))
  (run (cdr exp) '()))

(define (let-getvals2 exp)
  (define (run exp init)
    (cond
      ((eq? (cadr exp) 'and)
       (run (cddddr exp) (cons (car exp) init)))
      ((eq? (cadr exp) 'in)
       (reverse (cons (car exp) init)))))
  (run (cdddr exp) '()))

(define (let-getbody2 exp)
    (if (eq? (car exp) 'in)
        (cadr exp)
        (let-getbody2 (cddddr exp))))
;;3d slutt


(define (list-of-values exps env)
  (if (no-operands? exps)
      '()
      (cons (mc-eval (first-operand exps) env)
            (list-of-values (rest-operands exps) env))))

(define (eval-sequence exps env)
  (cond ((last-exp? exps) (mc-eval (first-exp exps) env))
        (else (mc-eval (first-exp exps) env)
              (eval-sequence (rest-exps exps) env))))

(define (eval-assignment exp env)
  (set-variable-value! (assignment-variable exp)
                       (mc-eval (assignment-value exp) env)
                       env)
  'ok)

(define (eval-definition exp env)
  (define-variable! (definition-variable exp)
                    (mc-eval (definition-value exp) env)
                    env)
  'ok)

;;; Selektorene / aksessorene som definerer syntaksen til uttrykk i språket 
;;; (seksjon 4.1.2, SICP)
;;; -----------------------------------------------------------------------

(define (self-evaluating? exp)
  (cond ((number? exp) #t)
        ((string? exp) #t)
        ((boolean? exp) #t)
        (else #f)))
  
(define (tagged-list? exp tag)
  (if (pair? exp)
      (eq? (car exp) tag)
      #f))

(define (quoted? exp)
  (tagged-list? exp 'quote))

(define (text-of-quotation exp) (cadr exp))

(define (variable? exp) (symbol? exp))

(define (assignment? exp)
  (tagged-list? exp 'set!))

(define (assignment-variable exp) (cadr exp))

(define (assignment-value exp) (caddr exp))


(define (definition? exp)
  (tagged-list? exp 'define))

(define (definition-variable exp)
  (if (symbol? (cadr exp))
      (cadr exp)
      (caadr exp)))

(define (definition-value exp)
  (if (symbol? (cadr exp))
      (caddr exp)
      (make-lambda (cdadr exp)
                   (cddr exp))))


(define (lambda? exp) (tagged-list? exp 'lambda))

(define (lambda-parameters exp) (cadr exp))
(define (lambda-body exp) (cddr exp))

(define (make-lambda parameters body)
  (cons 'lambda (cons parameters body)))


(define (if? exp) (tagged-list? exp 'if))

(define (if-predicate exp) (cadr exp))

(define (if-consequent exp) (caddr exp))

(define (if-alternative exp)
  (if (not (null? (cdddr exp)))
      (cadddr exp)
      'false))

(define (make-if predicate consequent alternative)
  (list 'if predicate consequent alternative))


(define (begin? exp) (tagged-list? exp 'begin))

(define (begin-actions exp) (cdr exp))

(define (last-exp? seq) (null? (cdr seq)))
(define (first-exp seq) (car seq))
(define (rest-exps seq) (cdr seq))

(define (sequence->exp seq)
  (cond ((null? seq) seq)
        ((last-exp? seq) (first-exp seq))
        (else (make-begin seq))))

(define (make-begin seq) (cons 'begin seq))


(define (application? exp) (pair? exp))
(define (operator exp) (car exp))
(define (operands exp) (cdr exp))

(define (no-operands? ops) (null? ops))
(define (first-operand ops) (car ops))
(define (rest-operands ops) (cdr ops))


(define (cond? exp) (tagged-list? exp 'cond))

(define (cond-clauses exp) (cdr exp))

(define (cond-else-clause? clause)
  (eq? (cond-predicate clause) 'else))

(define (cond-predicate clause) (car clause))

(define (cond-actions clause) (cdr clause))

(define (cond->if exp)
  (expand-clauses (cond-clauses exp)))

(define (expand-clauses clauses)
  (if (null? clauses)
      'false                          ; no else clause
      (let ((first (car clauses))
            (rest (cdr clauses)))
        (if (cond-else-clause? first)
            (if (null? rest)
                (sequence->exp (cond-actions first))
                (error "ELSE clause isn't last -- COND->IF:"
                       clauses))
            (make-if (cond-predicate first)
                     (sequence->exp (cond-actions first))
                     (expand-clauses rest))))))


;;; Evaluatorens interne datastrukturer for å representere omgivelser,
;;; prosedyrer, osv (seksjon 4.1.3, SICP):
;;; -----------------------------------------------------------------------

(define (false? x)
  (cond ((eq? x 'false) #t)
        ((eq? x #f) #t)
        (else #f)))

(define (true? x)
  (not (false? x)))
;; (som i SICP-Scheme'en vi tar med true/false som boolske verdier.)

(define (make-procedure parameters body env)
  (list 'procedure parameters body env))

(define (compound-procedure? p)
  (tagged-list? p 'procedure))


(define (procedure-parameters p) (cadr p))
(define (procedure-body p) (caddr p))
(define (procedure-environment p) (cadddr p))


(define (enclosing-environment env) (cdr env))

(define (first-frame env) (car env))

(define the-empty-environment '())

;; En ramme er et par der car er variablene
;; og cdr er verdiene:
(define (make-frame variables values)
  (cons variables values))

(define (frame-variables frame) (car frame))
(define (frame-values frame) (cdr frame))

(define (add-binding-to-frame! var val frame)
  (set-car! frame (cons var (car frame)))
  (set-cdr! frame (cons val (cdr frame))))

(define (extend-environment vars vals base-env)
  (if (= (length vars) (length vals))
      (cons (make-frame vars vals) base-env)
      (if (< (length vars) (length vals))
          (error "Too many arguments supplied:" vars vals)
          (error "Too few arguments supplied:" vars vals))))

;; Søker gjennom listene av variabel-bindinger i første ramme og 
;; så bakover i den omsluttende omgivelsen. (Moro; to nivåer av 
;; interne definisjoner med gjensidig rekursjon.) 
(define (lookup-variable-value var env)
  (define (env-loop env)
    (define (scan vars vals) 
      ; paralell rekursjon på listene av symboler og verdier
      (cond ((null? vars)
             (env-loop (enclosing-environment env)))
            ((eq? var (car vars))
             (car vals))
            (else (scan (cdr vars) (cdr vals)))))
    (if (eq? env the-empty-environment)
        (error "Unbound variable:" var)
        (let ((frame (first-frame env)))
          (scan (frame-variables frame)
                (frame-values frame)))))
  (env-loop env))

;; Endrer bindingen av 'var' til 'val' i en omgivelse 
;; (gir feil dersom 'var' ikke er bundet):
(define (set-variable-value! var val env)
  (define (env-loop env)
    (define (scan vars vals)
      (cond ((null? vars)
             (env-loop (enclosing-environment env)))
            ((eq? var (car vars))
             (set-car! vals val))
            (else (scan (cdr vars) (cdr vals)))))
    (if (eq? env the-empty-environment)
        (error "Unbound variable -- SET!:" var)
        (let ((frame (first-frame env)))
          (scan (frame-variables frame)
                (frame-values frame)))))
  (env-loop env))

;; define-variable! legger til en ny binding mellom 'var' og 'val' 
;; i den første rammen i omgivelsen 'env':
(define (define-variable! var val env)
  (let ((frame (first-frame env)))
    (define (scan vars vals)
      (cond ((null? vars)
             (add-binding-to-frame! var val frame))
            ((eq? var (car vars))
             (set-car! vals val))
            (else (scan (cdr vars) (cdr vals)))))
    (scan (frame-variables frame)
          (frame-values frame))))


;;; Håndtering av primitiver og den globale omgivelsen (SICP seksjon 4.1.4)
;;; -----------------------------------------------------------------------

(define (setup-environment)
  (let ((initial-env
         (extend-environment (primitive-procedure-names)
                             (primitive-procedure-objects)
                             the-empty-environment)))
    (define-variable! 'true 'true initial-env)
    (define-variable! 'false 'false initial-env)
    (define-variable! 'nil '() initial-env)
    initial-env))

(define the-global-environment the-empty-environment)
;; For initialisering av den globale omgivelsen, se kommentar til slutt i fila.

(define (primitive-procedure? proc)
  (tagged-list? proc 'primitive))

(define (primitive-implementation proc) (cadr proc))

(define primitive-procedures
  (list (list 'car car)
        (list 'cdr cdr)
        (list 'cons cons)
        (list 'null? null?)
        (list 'not not)
        (list '+ +)
        (list '- -)
        (list '* *)
        (list '/ /)
        (list '= =)
        (list 'eq? eq?)
        (list 'equal? equal?)
        (list 'display 
              (lambda (x) (display x) 'ok))
        (list 'newline 
              (lambda () (newline) 'ok))
        (list '1+                                    ;;Oppgave 2a
              (lambda (x) (+ x 1)))
        (list '1-                                    ;;Oppgave 2a
              (lambda (x) (- x 1)))
;;      her kan vi legge til flere primitiver.
        ))

(define (primitive-procedure-names)
  (map car
       primitive-procedures))

(define (primitive-procedure-objects)
  (map (lambda (proc) (list 'primitive (cadr proc)))
       primitive-procedures))

(define apply-in-underlying-scheme apply)

(define (apply-primitive-procedure proc args)
  (apply-in-underlying-scheme
   (primitive-implementation proc) args))


;;; Hjelpeprosedyrer for REPL-interaksjon (SICP seksjon 4.1.4)
;;; -----------------------------------------------------------------------

(define input-prompt ";;; MC-Eval input:")
(define output-prompt ";;; MC-Eval value:")

(define (read-eval-print-loop) ;;tilsvarer driver-loop i SICP
  (prompt-for-input input-prompt)
  (let ((input (read)))
    (let ((output (mc-eval input the-global-environment)))
      (announce-output output-prompt)
      (user-print output)))
  (read-eval-print-loop))

(define (prompt-for-input string)
  (newline) (newline) (display string) (newline))

(define (announce-output string)
  (newline) (display string) (newline))

(define (user-print object)
  (if (compound-procedure? object)
      (display (list 'compound-procedure
                     (procedure-parameters object)
                     (procedure-body object)
                     '<procedure-env>))
      (display object)))

'METACIRCULAR-EVALUATOR-LOADED

;;; For å starte read-eval-print loopen og initialisere 
;;; den globale omgivelsen, kjør:
;;; (set! the-global-environment (setup-environment))
;;; (read-eval-print-loop)

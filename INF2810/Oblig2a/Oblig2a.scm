(load "huffman.scm")

;;1a
(define (p-cons x y)
  (lambda (proc) (proc x y)))

(define (p-car proc)
  (list-ref (proc list) 0))

(define (p-cdr proc)
  (list-ref (proc list) 1))

;;(p-cons "foo" "bar")
;;(p-car (p-cons "foo" "bar"))
;;(p-cdr (p-cons "foo" "bar"))
;;(p-car (p-cdr (p-cons "zoo" (p-cons "foo" "bar"))))

;;1b
(define foo 42)

(define (test1b1)
  (lambda (x)
    (if (= foo x)
      'same
      'different)))

(define (test1b2)
  (lambda (x)
    (list x (list foo x))))

;;((test1b1) 4)
;;((test1b2) 'towel)

;;1c
(define foo (list 21 + 21))
(define baz (list 21 list 21))
(define bar (list 84 / 2))

(define (infix-eval foo)
  (if (not (= (length foo) 3))
      'lengdefeil
      ((list-ref foo 1) (list-ref foo 0) (list-ref foo 2))))

;;(infix-eval foo)
;;(infix-eval baz)
;;(infix-eval bar)

;;1d
(define bah '(84 / 2))
;;(infix-eval bah) 
;; Det er fordi i denne listen blir / lest "direkte" (tolkes som en "String") og kan ikke bli brukt som en procedure


;;2a
(define (member? pred item liste)
  (if (null? liste)
      #f
      (if (pred item (car liste))
          #t
          (member? pred item (cdr liste)))))

;;(member? = 4 '(1 2 3))

;;2c
(define (decode-hale bits tree)
  (define (decode-1 init bits current-branch)
    (if (null? bits)
        init
        (let ((next-branch
               (choose-branch (car bits) current-branch)))
          (if (leaf? next-branch)
              (decode-1 (append init (list (symbol-leaf next-branch))) (cdr bits) tree)
              (decode-1 init (cdr bits) next-branch)))))
  (decode-1 '() bits tree))

;;(decode-hale sample-code sample-tree)

;;2d
;; (ninjas fight ninjas by night)


;;2e

(define (encode meld tree)
  (define (encode-all init meld tree)
    (if (null? meld)
        init
        (encode-all (append init (encode-1 '() (car meld) tree)) (cdr meld) tree)))
  (define (encode-1 init meld tree)
    (cond
      ((leaf? tree) init)
      ((member? equal? meld (symbols (left-branch tree)))
        (encode-1 (append init (list 0)) meld (left-branch tree)))
      ((member? equal? meld (symbols (right-branch tree)))
        (encode-1 (append init (list 1)) meld (right-branch tree)))))      
  (encode-all '() meld tree))

;;(decode (encode '(ninjas fight ninjas by night) sample-tree) sample-tree)


;;2f
(define freqs '((a 2) (b 5) (c 1) (d 3) (e 1) (f 3)));

(define (grow-huffman-tree freqs)
  (define (grow-1 sets)
    (cond
      ((= 2 (length sets))
       (make-code-tree (car sets) (cadr sets)))
      (else (grow-1( adjoin-set (make-code-tree (car sets) (cadr sets)) (cddr sets))))))
 (grow-1 (make-leaf-set freqs)))

(define codebook (grow-huffman-tree freqs))
;;(decode (encode '(a b c) codebook) codebook)

;;2g
(define freqs2 '((samurais 57) (ninjas 20) (fight 45) (night 12) (hide 3) (in 2) (ambush 2) (defeat 1)
                               (the 5) (sword 4) (by 12) (assasin 1) (river 2) (forest 1) (wait 1) (poison 1)))

(define codebook2 (grow-huffman-tree freqs2))

;; - Meldingen bruker 42 bit.
;; - Gjennomsnittlengden per kodeord er 2,47 bits.
;; - 776 bits (En char 8 bits) (Ser bort fra mellomrom og linjeskift) Det er 97 char i meldingen, ganger man det
;;   med 8 (sizeof char) faar man 776

;;2h

(define (huffman-leaves tree)
  (define (find-weight init tree words)
    (if (null? words)
        init
        (find-weight (cons (list (car words) (look-in-tree tree (encode (list(car words)) tree))) init) tree (cdr words))))
  (define (look-in-tree tree kode)
    (cond
        ((null? kode)
         (weight tree))
      ((= 0 (car kode))
       (look-in-tree (left-branch tree) (cdr kode)))
      ((= 1 (car kode))
       (look-in-tree (right-branch tree) (cdr kode)))))
  (find-weight '() tree (caddr tree)))

;;(huffman-leaves sample-tree)

;;2i

(define (expected-codeword-length tree)
  (define (word-value init tree nodes)
    (if (null? nodes)
        init
        (word-value (+ init (* (/ (cadar nodes)(cadddr tree)) (length (encode (list(caar nodes)) tree)))) tree (cdr nodes))))
  (word-value 0 tree (huffman-leaves tree)))

;;(expected-codeword-length sample-tree)
  
      
      
      
        


  

        
        



    

             
        
              
        


  




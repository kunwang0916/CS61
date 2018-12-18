(define (cddr s)
  (cdr (cdr s)))

(define (cadr s)
  'YOUR-CODE-HERE
  (car (cdr s))
)

(define (caddr s)
  'YOUR-CODE-HERE
  (car (cddr s))
)

(define (sign x)
  'YOUR-CODE-HERE
  (cond ((> x 0) 1) ((= x 0) 0) (else -1))
)

(define (square x) (* x x))

(define (pow b n)
  'YOUR-CODE-HERE
  (cond 
    ((= 1 n) b) 
    ((= 0 n) 1) 
    ((= 2 n) (square b)) 
    ((even? n) (square (pow b (/ n 2))))
    (else (* b (pow b (- n 1))))
  )
)

(define (ordered? s)
    (cond 
      ((or (null? s) (null? (cdr s))) true)
      (else (and (<= (car s) (cadr s)) (ordered? (cdr s))))
    )
)

(define (empty? s) (null? s))

(define (add s v)
    'YOUR-CODE-HERE
    )

; Sets as sorted lists
(define (contains? s v)
    'YOUR-CODE-HERE
    )

; Equivalent Python code, for your reference:
;
; def empty(s):
;     return s is Link.empty
;
; def contains(s, v):
;     if empty(s):
;         return False
;     elif s.first > v:
;         return False
;     elif s.first == v:
;         return True
;     else:
;         return contains(s.rest, v)

(define (intersect s t)
    'YOUR-CODE-HERE
    )

; Equivalent Python code, for your reference:
;
; def intersect(set1, set2):
;     if empty(set1) or empty(set2):
;         return Link.empty
;     else:
;         e1, e2 = set1.first, set2.first
;         if e1 == e2:
;             return Link(e1, intersect(set1.rest, set2.rest))
;         elif e1 < e2:
;             return intersect(set1.rest, set2)
;         elif e2 < e1:
;             return intersect(set1, set2.rest)

(define (union s t)
    'YOUR-CODE-HERE
    )

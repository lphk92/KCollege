#lang scheme
; This program will remove the given item from the list, and is able to handle
; the removal of both atoms and sublists.
;
; Author: Lucas Kushner

(define (remove target lis)
  (cond
    ((null? lis) '())
    ((equal? (car lis) target) (append '() (remove target (cdr lis))))
    (else (append (list (car lis)) (remove target (cdr lis)))) 
  ))

(remove 'a '(b a a a a a c d f a))
(remove '(a b) '(b a (a b) (c d) e (a b) f))

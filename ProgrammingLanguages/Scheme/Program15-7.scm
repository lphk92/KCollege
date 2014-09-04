#lang scheme
; This program will remove a given atom from a given list.
;
; Author: Lucas Kushner

(define (removeAtom atom lis)
  (cond
    ((null? lis) '())
    ((eq? (car lis) atom) (append '() (removeAtom atom (cdr lis))))
    (else (append (list (car lis)) (removeAtom atom (cdr lis)))) 
  ))

(removeAtom 'a '(b a a a a a c d f a))

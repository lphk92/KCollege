#lang scheme
; This program defines a function that simply reverses a list
;
; Author: Lucas Kushner

(define (reverse lis)
  (cond 
    ((null? (cdr lis)) lis)
    (else (append (reverse (cdr lis)) (list (car lis))))
  ))

(reverse '(a b c d e))

#lang scheme
; This program defines functions for determining the largest and
; smallest numbers from a given list, as well as a function combining
; these two results to return the extrema of the list
;
; Author: Lucas Kushner

; Find the largest item in a given list
(define (largest lis)
  (if (null? (cdr lis))
      (car lis)
      (if (> (car lis) (largest (cdr lis)))
          (car lis)
          (largest (cdr lis))
          )
      )
  )

; Find the smallest item in a given list
(define (smallest lis)
  (if (null? (cdr lis))
      (car lis)
      (if (< (car lis) (smallest (cdr lis)))
          (car lis)
          (smallest (cdr lis))
          )
      )
  )

; Find the extrema of a given list
(define (extrema lis)
  (cons (smallest lis) (largest lis)))

(extrema '(1 4 8 93 7 8 -29 38 -1 3))

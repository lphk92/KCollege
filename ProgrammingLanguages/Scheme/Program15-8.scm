#lang scheme
; This program simply defines a function to remove the last item in a list,
; like a cdr, but for the end of the list instead of the beginning.
;
; Author: Lucas Kushner

(define (leaveBehind lis)
  (if (null? (cdr lis))
      '()
      (cons (car lis) (leaveBehind (cdr lis)))
      )
  )

(leaveBehind '(a b c d e (f g) h))

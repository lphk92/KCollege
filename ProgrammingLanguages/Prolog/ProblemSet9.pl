/*
An exploration of the Prolog language, in fulfillment of 
Problem Set #9

by Lucas Kushner
*/

female(shelley).
female(mary).
male(bill).
male(jake).
parent(bill, jake).
parent(bill, shelley).
parent(mary, jake).
parent(mary, shelley).

%Determines if X is the mother of Y
mother(X, Y) :- parent(X, Y), female(X).

%Determines if X is the sister of Y
sister(X, Y) :- parent(Z, X), parent(Z, Y), female(X).

%Finds the maximum value in a list
max([X], X).
max([H | T], H) :- max(T, X), X =< H.
max([H | T], X) :- max(T, X), X > H.

%Determines if a particular element is in a list
member(Element, [Element | _]).
member(Element, [_ | List]) :- member(Element, List).

%Determiens if the intersection of two lists is empty
emptyIntersect([], [_|_]).
emptyIntersect([H | T], L) :- not(member(H, L)), emptyIntersect(T, L).

%Produces the union of two lists
combo([A|B], C, D) :- member(A,C), !, combo(B,C,D).
combo([A|B], C, [A|D]) :- combo(B,C,D).
combo([],X,X).

%Find the last element in a list
final([X], X).
final([_ | T], X) :- final(T, X).

%Implements a Quicksort algorithm
quicksort([], []).
quicksort([H | T], S) :- partition(H, T, L, R),
                         quicksort(L, SortL),
                         quicksort(R, SortR),
                         appendo(SortL, [H | SortR], S).

partition(P, [], [], []).
partition(P, [H | T], [H | L], R) :- H @=< P, partition(P, T, L, R).
partition(P, [H | T], L, [H | R]) :- H @> P, partition(P, T, L, R).

appendo([], L, L).
appendo([H | L1], L2, [H | L3]) :- append(L1, L2, L3).

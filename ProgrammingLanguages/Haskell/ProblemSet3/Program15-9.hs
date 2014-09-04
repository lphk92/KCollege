-- These programs were created by a collaboration of the following authors:
-- Caitlin Braun
-- Pan Fayang
-- Will Guedes
-- Lucas Kushner

import TestSuiteSupportModule

-- Function for removing all instances of a given item from the list --
removeAtom :: Eq t => t -> [t] -> [t]
removeAtom atom lis = [ a | a <- lis, atom /= a]

-- Tests the removeAtom method --
testSuite = TestSuite "Tests removeAtom"
            [Test "basic" (removeAtom 1 [1, 2, 1, 4] == [2, 4]),
             Test "tuple" (removeAtom (1, 2) [(2, 3), (1, 2)] == [(2, 3)]),
             Test "empty" (removeAtom 1 [1, 1, 1] == []),
             Test "not-in-list" (removeAtom 2 [1, 0, -3] == [1, 0, -3])]

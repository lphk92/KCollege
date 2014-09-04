-- These programs were created by a collaboration of the following authors:
-- Caitlin Braun
-- Pan Fayang
-- Will Guedes
-- Lucas Kushner

import TestSuiteSupportModule

-- Function for reversing the order of items in a list --
rev :: [t] -> [t]
rev lis = reverse lis

-- Tests for the rev function --
testSuite = TestSuite "Tests reverse"
            [Test "basic" (reverse [1, 2, 3] == [3, 2, 1]),
             Test "empty" (reverse ([] :: [Int]) == []),
             Test "sublist" (reverse [[1, 2], [3, 4]] == [[3, 4], [1, 2]]),
             Test "tuple" (reverse [(1, 2), (3, 4)] == [(3, 4), (1, 2)]),
             Test "char" (reverse "hello world" == "dlrow olleh")]

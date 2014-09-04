-- These programs were created by a collaboration of the following authors:
-- Caitlin Braun
-- Pan Fayang
-- Will Guedes
-- Lucas Kushner

import TestSuiteSupportModule

-- Function for removing the last item of a list --
leaveBehind :: [t] -> [t]
leaveBehind lis = init lis

-- Tests for leaveBehind --
testSuite = TestSuite "Tests leaveBehind"
            [Test "basic" (leaveBehind [1, 2, 3] == [1, 2]),
             Test "to-empty" (leaveBehind [1] == []),
             Test "tuple" (leaveBehind [(1, 2), (3, 4)] == [(1, 2)]),
             Test "char" (leaveBehind "hello world" == "hello worl")]

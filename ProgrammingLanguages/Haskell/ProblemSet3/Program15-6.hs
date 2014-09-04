-- These programs were created by a collaboration of the following authors:
-- Caitlin Braun
-- Pan Fayang
-- Will Guedes
-- Lucas Kushner

import TestSuiteSupportModule

-- Function for finding the extrema of a list --
extrema :: Ord t => [t] -> (t, t)
extrema lis = ((minimum lis), (maximum lis))

-- Tests for extrema --
testSuite = TestSuite "Tests extrema"
            [Test "basic" (extrema [1, 2, 3] == (1, 3)),
             Test "negative-min" (extrema [1, 2, 3, -4] == (-4, 3)),
             Test "negative-max" (extrema [-1, -2, -5, -7] == (-7, -1)),
             Test "repeat-max" (extrema [0, 1, 2, 2] == (0, 2)),
             Test "repeat-min" (extrema [-1, -1, 0, 1, 2] == (-1, 2)),
             Test "zero-pad" (extrema [0, 1, 2, 3, 4, 0] == (0, 4)),
             Test "same" (extrema [1, 1, 1, 1] == (1, 1)),
             Test "loner" (extrema [1] == (1, 1))]

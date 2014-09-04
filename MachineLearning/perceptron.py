"""
This class provides an implementation for a basic, single layer perceptron with
any number of inputs and 1 output.

@author Lucas Kushner
"""

from numpy import *

def trainPerceptron(inputs, targets, eta, iterations):
	
	# From the data, determine the number of input nodes, output nodes, and data points.
	numInputs = shape(inputs)[1]
	numOutputs = 1
	numData = shape(inputs)[0]
	
	# Generate the weight vector with values randing from -0.05 to 0.05. 
	# It has numInputs + 1 columns to account for the weight of the bias.
	weights = random.random(numInputs + 1) * 0.1 - 0.05
	
	# Append a column of negative ones to the inputs. 
	# These represent the "input" value of the bias node.
	inputs = append(inputs, -ones((shape(inputs)[0], 1)), axis=1)
	
	# Run the training for the given number of iterations.
	# If a correct answer is found before all of these iterations, it will break out of the for loop.	
	solutionFound = False
	for i in range (0, iterations):	
		activations = zeros(numData)

		for j in range(0, numData):
			inputVector = inputs[j]
			activation = 0
		
			# Calculate the activation functions based on the various inputs
			for k in range (0, len(inputVector)):
				activation += inputVector[k] * weights[k]
			
			activations[j] = 1 if activation > 0 else 0
			
		# If the activations are equal to the targets, we have found a solution
		if areEqual(activations, targets):
			print "Solution Found\nFinal Weights: \n", weights
			solutionFound = True
			break
			
		# Adjust the weights if a solution has not yet been found
		weights += eta * dot(transpose(inputs), targets - activations)
		
	if solutionFound == False:
		print "No Solution Found\n"
			
def areEqual(array1, array2):

	for i in range(0, len(array1)):
		if array1[i] != array2[i]:
			return False;
			
	return True		
	
	
def main():

	# Test the logical OR
	print "\nTest Logical OR"
	trainPerceptron([[0, 0],[0, 1],[1, 0],[1, 1]], [0, 1, 1, 1], 0.25, 10)
	
	# Test the logical AND
	print "\nTest Logical AND"
	trainPerceptron([[0, 0],[0, 1],[1, 0],[1, 1]], [0, 0, 0, 1], 0.25, 10)

	# Test the logical NOT
	print "\nTest Logical NOT"
	trainPerceptron([[0],[1]], [1,0], 0.25, 10)
	
	# Test the logical NOR
	print "\nTest Logical NOR"
	trainPerceptron([[0, 0],[0, 1],[1, 0],[1, 1]], [1, 0, 0, 0], 0.25, 10)
	
	# Test the logical NAND
	print "\nTest Logical NAND"
	trainPerceptron([[0, 0],[0, 1],[1, 0],[1, 1]], [1, 1, 1, 0], 0.25, 10)

	# Test the logical XOR
	print "\nTest Logical XOR"
	trainPerceptron([[0, 0],[0, 1],[1, 0],[1, 1]], [0, 1, 1, 0], 0.25, 10)

	print

if __name__ == "__main__":
	main()

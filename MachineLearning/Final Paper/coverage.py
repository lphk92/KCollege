"""
This class provides an implementation for a decision tree

@author Lucas Kushner
"""

class CoverageNode:
	"""
	This class represents a node of a tree (data structure).
	
	Each node keeps track of its own name, and the links that it has.
	Each of the links contains the connecting node and its probability (weight)
	"""

	def __init__(self, feature):
		self.feature = feature
		self.children = {}
		
	def printTree(self):
		print self.feature
		for bin in self.children:
			self.children[bin].printTree()
		
	def addChild(self, binLimits, childNode):
		self.children[binLimits] = childNode
		
	def train(self, data, target):
		
		# Currently, this method will override any previous value of the leaf node
		# Could be updated to keep track of values in this node and at the end of training, use the most common value
		if len(self.children) == 0:
			self.feature = target
			#print "Output Set --- ", self.feature
			
		else:
			index = self.feature
			value = data[index]
		
			for bin in self.children:
				if bin[0] <= value and bin[1] >= value:
					self.children[bin].train(data, target)
					
		
	def determine(self, data):		
	
		# If the current node has no children then it is a leaf node, and therefore
		# is the predicted output for the data
		if len(self.children) == 0:
			return self.feature
			
		#Otherwise, recursive traverse the tree until you reach a leaf
		else:
			index = self.feature
			value = data[index]
		
			for bin in self.children:
				if bin[0] <= value and bin[1] >= value:
					return self.children[bin].determine(data)
		

from numpy import *	

def readData(startLine, endLine, numFeatures):
	""" Read the given lines of input and output data from the data file covtype.data """
	
	# Read in the lines of data from the file
	f = open('covtype.data', 'r')
	lines = f.readlines()[startLine : endLine]
		
	inputs = []
	outputs = []
	
	# Parse the data into integers, then choose only the desired data 
	for line in lines:
		parsedValues = [int(value) for value in line.split(',')]
		inputs.append(parsedValues[0:numFeatures])
		outputs.append(parsedValues[54])
		
	return hstack((array(inputs), array(outputs).reshape(endLine - startLine,1)))	
	
def calculateFeatureStatistics(data, numBins):
	"""
	Calculates the probaility of a data to fall into a particular bin.
	
	data should be a vector containing all of the data for a particular feature.
	
	Returns the range of the bins, as well as
	an array of probabilities which match up with the bins.
	"""
	
	# For now, just use the min and max values of the data to uniformly create bins
	binSize = math.ceil((max(data) - min(data))/numBins)
#print min(data), " - ", max(data)
	
	bins = []	
	binCounts = []
	
	lastBinMin = min(data)
	for i in range(0, numBins):
		bins.append((lastBinMin, lastBinMin + binSize))
		binCounts.append(0)
		lastBinMin += binSize + 1
	
	for i in range(0, len(data)):
		for j in range (0, len(bins)):
			if data[i] >= bins[j][0] and data[i] <= bins[j][1]:	
				binCounts[j] += 1
				
	binProbabilities = [double(count)/len(data) for count in binCounts]
	
	return bins, binProbabilities
	
def calculateFeatureEntropy(binProbabilities):
	"""
	Calculates the total entropy of a given feature.
	
	This will be done by first generating an entropy matrix for that feature,
	where the columns are the different ranges of values for that feature 
	(ie. if elevation goes from 1000 - 2000 there may be 4 columns, 1000-1250....1750-2000)
	and the rows are the different types of trees.
	
	Then, to find the total feature entropy we sum up all of the entropies in the matrix.
	
	The reason we do this, is we will organize the features in the tree with higher entropy features
	being in the top levels of the tree.
	"""
	
	# The way this works right now is just by summing the entropies from the 
	# probabilities of falling into a certain bin.
	totalEntropy = 0
	for p in binProbabilities:
		totalEntropy += calculateEntropy(p)
		
	return totalEntropy

def calculateEntropy(p):
	""" Calculates the entropy of the given number. """
	
	if p != 0:
		return -p * log2(p)
	else:
		return 0

def createTree(dataTuples):
	""" 
	Recursively creates the decision tree.
	This method assumes that the dataTuples are sorted in descending order of feature entropy.
	"""
	
	dataTuples = dataTuples[:]

	if len(dataTuples) == 0:
		# Return a value of 0 which will 
		# later be replaced with the kind of tree that will be found at this leaf
		#print "LEAF CREATED"
		return CoverageNode(0)
		
	tree = CoverageNode(dataTuples[0][1])
	bins = dataTuples[0][2]
	
	shavedTuples = dataTuples[1:len(dataTuples)]
	
	for bin in bins:
		tree.addChild(bin, createTree(shavedTuples))
	
	return tree
	
def calculateError(predictionResultMatrix):

	wrongAnswers = 0
	for entry in predictionResultMatrix:
		if entry != 0:
			wrongAnswers += 1
			
	return (float(wrongAnswers) / predictionResultMatrix.size) * 100
	
def run(numTrainingData, numTestData, numFeatures, numBins):

	trainingData = readData(0, numTrainingData, numFeatures)
	testData = readData(numTrainingData + 1, numTrainingData + numTestData + 1, numFeatures)

	# Find the bins, probabilities and entropies for the different variables
	dataTuples = []

	for i in range(trainingData.shape[1]-1):
		values = trainingData[:,i]
		bins, probs = calculateFeatureStatistics(values, numBins)
		entropy = calculateFeatureEntropy(probs)
	
		dataTuples.append((entropy, i, bins, probs))
	
	# Sort the list of tuples so to find the order in which they should be added in the tree
	dataTuples = sorted(dataTuples, reverse = True)		

	tree = createTree(dataTuples)

	# Train the tree
	for entry in trainingData:
		#print "Training: ", entry
		tree.train(entry, entry[len(entry)-1])

	# Verify the tree will accurately predict all training data
	predictions = []
	for entry in trainingData:
		predictions.append(tree.determine(entry))

	# Every non-zero entry in the following array is a mis-prediction
	predictionResultTrainingMatrix = abs(array(predictions) - trainingData[:, trainingData.shape[1]-1])
	#print  calculateError(predictionResultTrainingMatrix)
	#print array(predictions), "\n", predictionResultTrainingMatrix, "\n", calculateError(predictionResultTrainingMatrix)


	#The following code will make predictions using test data
	
	# See how well the tree predicts the test data
	predictions = []
	for entry in testData:
	
		# Check if the predicted value is "None". This indicates that one of the
		# values in the input vector fell out of the range for that feature determined in training.
		pred = tree.determine(entry)
		if (pred == None):
			#print entry
			pred = 100
		
		predictions.append(pred)

	# TODO: Figure out what's going on with this
	# Turns out that in testing, sometimes there are leaf nodes with a value of 0 which are chosen
	# This will no longer throw an error, however it should be fixed
	# It seems to be that the more bins, the more "0" predictions are made
	# 
	# Update: This error is caused by test values who are outside of the range of the training values
	# To fix this, we assign check if any predicted values are None, and then assign them a large positive integer
	predictionResultTestMatrix = abs(array(predictions) - testData[:, testData.shape[1]-1])
	#print array(predictions), "\n", predictionResultTestMatrix, "\n", calculateError(predictionResultTestMatrix)
	print calculateError(predictionResultTestMatrix)


def main():

	numTrainingData = 400
	numTestData = 500
	numFeatures = 3
	numBins = 3

	for i in range(0, 15):
		for j in range(0, 10):
			print numFeatures, numBins,
			run(numTrainingData, numTestData, numFeatures, numBins)
			numFeatures += 1
		numBins += 1
	
	print "\nDone."	

if __name__ == "__main__":
	main()
					

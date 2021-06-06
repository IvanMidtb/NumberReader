Project Description

Goal: The goal of this project was to create a neural network that could read handwritten numbers between 0-9 and return the appropriate number using a backpropagation deep learning algorithm

How to use: 
1. Create a folder source named "data" inside the project to hold the images of the numbers
2. Run IdxReader. This will fill the folder data with 60000 images, this will take a while
3. Run the main class for a demo of the programs function

Features:
- My project includes a neural network class, this class creates a directed graph in which nodes or neurons are divided into layer, and each neuron connects to each neuron in the next layer.
The first layer is mean for input and the last for output. When creating a network, and array of ints must be given with each index of the array corresponding to the layer number and the number in
the array corresponding the number of neurons in the layer. When the network is created, random values distributed with a normal curve are assigned to the connections between neurons, these are called
weights and a random bias is generated for each neuron, except input neurons. These weights and biases are stored in matrixes, a special class that I made that stores 2D arrays of doubles and can perform
some linear algebra.
- When using the method run of the network class, a given input, in the form of an array of doubles, will be ran through the network to produce and array of doubles that is the output.
- The method SGD, short for stochastic gradient descent, will take a DataSet of training data, an int for the number of runs (epochs), an int for the number of data points to use in each run,
a double that affects the speed of training (eta), and an optional DataSet for testing. This method does the deep learning and will run through the data, and perform backpropagation to improve the network
- The DataPoint class will store an input for a network and the expect output
- The DataSet class stores a set of DataPoints and is intended to be given to the network for training or testing. This class can have points added or randomly removed
- The FileAid class is mean to read the png of the handwritten images and convert them to inputs or to DataSets for the network
	- The getIntensities method returns the pixel data of a specified png
	- The CreateImageDataSet method will create a DataSet of the images in the trainingData folder
- While the network class was made with handwritten numbers in mind, it could be used to create a neural network for other purposes as long as an array of doubles is provided as input and DataSets are created
to train the network

Challenges/ triumphs:
- The first challenge was transforming the data provided online by MNIST to usable data, to do this I used stack overflow's user RayDeeA's code to convert the .idx3-ubyte data into .png's
this code can be found in the IdxReader.java file or at https://stackoverflow.com/questions/17279049/reading-a-idx-file-type-in-java.
- The next challenge was transforming the images into usable data, but after reading online, I found a way to convert each pixel into a double
- Creating the network as a data type was not to hard, but I had to create a class to handle linear algebra and to store matrixies and vectors since I could not figure out how to download or use
available libraries from the internet, this while a challenge, improved my understanding of the underling mathematics and helped me learn some linear algebra
- The biggest challenge was creating the backpropagation algorithm as this required some intense reading that included multivariable calculus, even now I am still unsure of whether I understand the mathematics.
This part of the program is the part that is still faulty as the  best network I created had a 12% success rate at guessing numbers 0-9, only marginally better than guessing. After spending a long amount of time
trying to figure out why it does not improve much, I am still not sure of the reasons.

Resources used:
Code to convert .idx3-ubyte data into .png's by RayDeeA: https://stackoverflow.com/questions/17279049/reading-a-idx-file-type-in-java
Data used from MNIST: http://yann.lecun.com/exdb/mnist/
Explanations of neural networks and deep learning from Three blue One Brown youTube channel: https://www.youtube.com/watch?v=aircAruvnKk&list=PLZHQObOWTQDNU6R1_67000Dx_ZCJB-3pi
Neural Networks and Deep Learning by Michael NielsenProject Description
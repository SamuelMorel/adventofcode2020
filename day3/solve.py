# global param for fileName
fileName = "input.txt"

def solve(right: int, down: int):
    # init right index
    position = right;
    countTrees = 0;
    # open file read only
    with open(fileName, 'r') as fo:
        reader = fo.readlines()
        # use first line to get length
        maxPosition = len(reader[0])-1;
        # read other lines and skip every X lines
        for line in reader[down::down]: 
            # if the file is a tree
            if line[position] == '#':
                countTrees+=1
            # move right
            position+=right
            # check if we're at end of line
            if (position >= maxPosition):
                position-=maxPosition
    return countTrees

# Step 1
print(solve(1, 3))

# Step 2
def solveMuliple(tuples: list):
    product = 1;
    for right, down in tuples:
        product *= solve(right, down)

    return product

print(solveMuliple([(1, 1), (3, 1), (5, 1), (7, 1) ,(1, 2)]))

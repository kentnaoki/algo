#include "RandomPuzzle.h"
#include <random>

RandomPuzzle::RandomPuzzle(int size) : size(size) {
    basePuzzle = generatePuzzle(size);
}

std::vector<std::vector<int>> RandomPuzzle::generatePuzzle(int size) {
    std::vector<std::vector<int>> puz(size, std::vector<int>(size));
    int count = 1;
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            puz[i][j] = count;
            count++;
        }
    }
    int row = size - 1;
    int col = size - 1;
    puz[row][col] = 0;
    return puz;
}

std::vector<std::vector<int>> RandomPuzzle::getBasePuzzle() const {
    return basePuzzle;
}

std::vector<std::vector<int>> RandomPuzzle::getRandomisedPuzzle() {
    std::vector<std::vector<int>> puzzle(size, std::vector<int>(size));
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            puzzle[i][j] = basePuzzle[i][j];
        }
    }

    int row = size - 1;
    int col = size - 1;
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<int> iterDist(0, 9999);
    std::uniform_int_distribution<int> moveDist(0, 3);
    
    int iter = iterDist(gen);
    std::map<int, std::vector<int>> dirMap;
    dirMap[0] = {-1, 0};
    dirMap[1] = {1, 0};
    dirMap[2] = {0, -1};
    dirMap[3] = {0, 1};

    int i = 0;
    while (i < iter) {
        int move = moveDist(gen);
        std::vector<int> dir = dirMap[move];

        int newRow = row + dir[0];
        int newCol = col + dir[1];

        if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
            puzzle[row][col] = puzzle[newRow][newCol];
            puzzle[newRow][newCol] = 0;

            row = newRow;
            col = newCol;
            i++;
        }
    }

    return puzzle;
}
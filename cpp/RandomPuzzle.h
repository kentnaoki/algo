#ifndef RANDOM_PUZZLE_H
#define RANDOM_PUZZLE_H

#include <vector>
#include <map>

class RandomPuzzle {
private:
    std::vector<std::vector<int>> basePuzzle;
    int size;

    std::vector<std::vector<int>> generatePuzzle(int size);

public:
    RandomPuzzle(int size);
    std::vector<std::vector<int>> getBasePuzzle() const;
    std::vector<std::vector<int>> getRandomisedPuzzle();
};

#endif
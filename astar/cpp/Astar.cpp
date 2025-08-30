#include "Astar.h"
#include "RandomPuzzle.h"
#include "Solver.h"
#include "Encoder.h"
#include "Heuristic.h"
#include <iostream>
#include <sstream>

int main(int argc, char* argv[]) {
    if (argc != 4) {
        std::cerr << "Usage: " << argv[0] << " <size> <sampleSize> <heuristicType>" << std::endl;
        return 1;
    }
    
    int size = std::stoi(argv[1]);
    int sampleSize = std::stoi(argv[2]);
    std::string heuristicType = argv[3];

    RandomPuzzle random(size);
    std::vector<std::vector<int>> goal = random.getBasePuzzle();
    auto heuristic = HeuristicFactory::getHeuristic(heuristicType, size);

    long totalNodes = 0;
    long totalTimeMs = 0;

    for (int i = 0; i < sampleSize; i++) {
        std::vector<std::vector<int>> puz = random.getRandomisedPuzzle();
        Solver solver(HeuristicFactory::getHeuristic(heuristicType, size), 
                     std::make_unique<LongEncoder>(size));
        std::vector<long> result = solver.solvePuzzle(puz, goal);

        totalNodes += result[0];
        totalTimeMs += result[1];
    }

    double totalTimeSeconds = totalTimeMs / 1000000.0;
    double average = (totalTimeSeconds > 0) ? totalNodes / totalTimeSeconds : 0;

    std::cout << "-------------------------------" << std::endl;
    std::cout << static_cast<int>(average) << " Nodes/second" << std::endl;

    return 0;
}

std::string puzToString(const std::vector<std::vector<int>>& puzzle) {
    std::stringstream ss;
    for (size_t i = 0; i < puzzle.size(); i++) {
        ss << "[";
        for (size_t j = 0; j < puzzle[i].size(); j++) {
            ss << puzzle[i][j];
            if (j < puzzle[i].size() - 1) ss << ", ";
        }
        ss << "]";
        if (i < puzzle.size() - 1) ss << "\n";
    }
    return ss.str();
}
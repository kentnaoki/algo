#ifndef SOLVER_H
#define SOLVER_H

#include "Node.h"
#include "Encoder.h"
#include "Heuristic.h"
#include <vector>
#include <queue>
#include <unordered_map>
#include <memory>

class Solver {
private:
    std::vector<std::vector<int>> direction;
    std::priority_queue<Node*, std::vector<Node*>, bool(*)(Node*, Node*)> openList;
    std::unordered_map<long, int> closedList;
    std::unique_ptr<Heuristic> heuristic;
    std::unique_ptr<Encoder> encoder;

    std::vector<int> arrayTo1d(const std::vector<std::vector<int>>& puz);
    std::vector<int> findZero(const std::vector<std::vector<int>>& puz);
    std::vector<int> getDeepCopy(const std::vector<int>& arr);

public:
    Solver(std::unique_ptr<Heuristic> heuristic, std::unique_ptr<Encoder> encoder);
    ~Solver();
    std::vector<long> solvePuzzle(const std::vector<std::vector<int>>& puz, 
                                  const std::vector<std::vector<int>>& goal);
};

#endif
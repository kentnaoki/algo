#include "Solver.h"
#include <chrono>
#include <algorithm>

bool compareNodes(Node* a, Node* b) {
    return a->score > b->score;
}

Solver::Solver(std::unique_ptr<Heuristic> heuristic, std::unique_ptr<Encoder> encoder)
    : heuristic(std::move(heuristic)), encoder(std::move(encoder)), openList(compareNodes) {
    direction.push_back({-1, 0});
    direction.push_back({1, 0});
    direction.push_back({0, -1});
    direction.push_back({0, 1});
}

Solver::~Solver() {
    while (!openList.empty()) {
        delete openList.top();
        openList.pop();
    }
}

std::vector<long> Solver::solvePuzzle(const std::vector<std::vector<int>>& puz, 
                                      const std::vector<std::vector<int>>& goal) {
    auto start = std::chrono::high_resolution_clock::now();
    std::vector<int> goal1d = arrayTo1d(goal);
    long encodedGoal = encoder->encode(goal1d);
    std::vector<int> zeroCor = findZero(puz);
    int puzSize = puz.size();
    std::vector<int> puz1d = arrayTo1d(puz);
    
    Node* node = new Node(puz1d, zeroCor, nullptr, 0, 0, heuristic->getHeuristic(puz1d));
    openList.push(node);
    int expanded = 0;

    while (!openList.empty()) {
        expanded++;
        Node* curNode = openList.top();
        openList.pop();
        long key = encoder->encode(curNode->puzzle);

        if (closedList.find(key) != closedList.end() && curNode->startToN > closedList[key]) {
            delete curNode;
            continue;
        }

        closedList[key] = curNode->startToN;

        int zeroRow = curNode->zeroCor[0];
        int zeroCol = curNode->zeroCor[1];

        for (const auto& dir : direction) {
            int newRow = zeroRow + dir[0];
            int newCol = zeroCol + dir[1];

            std::vector<int> curPuz = curNode->puzzle;

            if (newRow < 0 || newRow >= puzSize || newCol < 0 || newCol >= puzSize) {
                continue;
            }

            int zeroIdx = zeroRow * puzSize + zeroCol;
            int newIdx = newRow * puzSize + newCol;
            int val = curPuz[newIdx];
            int tmp = curPuz[zeroIdx];
            curPuz[zeroIdx] = curPuz[newIdx];
            curPuz[newIdx] = tmp;

            long encodedCurPuz = encoder->encode(curPuz);

            if (expanded == 1000000 || encodedCurPuz == encodedGoal) {
                auto end = std::chrono::high_resolution_clock::now();
                auto timeElapsed = std::chrono::duration_cast<std::chrono::microseconds>(end - start).count();
                
                // Clean up memory
                delete curNode;
                while (!openList.empty()) {
                    delete openList.top();
                    openList.pop();
                }
                
                return {expanded, timeElapsed};
            }

            int startToN = curNode->startToN + 1;
            int heuristicCost = heuristic->updateHeuristic(curNode->heuristic, val, newIdx, zeroIdx);
            int score = startToN + heuristicCost;

            Node* nextNode = new Node(getDeepCopy(curPuz), {newRow, newCol}, curNode, score, startToN, heuristicCost);

            if (closedList.find(encodedCurPuz) == closedList.end() || startToN < closedList[encodedCurPuz]) {
                closedList[encodedCurPuz] = startToN;
                openList.push(nextNode);
            } else {
                delete nextNode;
            }

            tmp = curPuz[zeroIdx];
            curPuz[zeroIdx] = curPuz[newIdx];
            curPuz[newIdx] = tmp;
        }

        delete curNode;
    }

    return {-1, -1};
}

std::vector<int> Solver::arrayTo1d(const std::vector<std::vector<int>>& puz) {
    int newSize = puz.size() * puz.size();
    std::vector<int> copy(newSize);

    for (int row = 0; row < puz.size(); row++) {
        for (int col = 0; col < puz.size(); col++) {
            int index = row * puz.size() + col;
            copy[index] = puz[row][col];
        }
    }
    return copy;
}

std::vector<int> Solver::findZero(const std::vector<std::vector<int>>& puz) {
    for (int row = 0; row < puz.size(); row++) {
        for (int col = 0; col < puz.size(); col++) {
            if (puz[row][col] == 0) {
                return {row, col};
            }
        }
    }
    return {0, 0};
}

std::vector<int> Solver::getDeepCopy(const std::vector<int>& arr) {
    return arr;
}
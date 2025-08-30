#ifndef NODE_H
#define NODE_H

#include <vector>

struct Node {
    std::vector<int> puzzle;
    std::vector<int> zeroCor;
    Node* parent;
    int score;
    int startToN;
    int heuristic;
    
    Node(const std::vector<int>& puzzle, const std::vector<int>& zeroCor, 
         Node* parent, int score, int startToN, int heuristic)
        : puzzle(puzzle), zeroCor(zeroCor), parent(parent), 
          score(score), startToN(startToN), heuristic(heuristic) {}
};

#endif
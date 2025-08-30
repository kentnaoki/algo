#include "Heuristic.h"
#include <algorithm>
#include <stdexcept>
#include <cmath>

// NoHeuristic implementation
int NoHeuristic::getHeuristic(const std::vector<int>& state) {
    return 0;
}

int NoHeuristic::updateHeuristic(int oldH, int val, int oldIdx, int newIdx) {
    return 0;
}

// MisplacedTileHeuristic implementation
MisplacedTileHeuristic::MisplacedTileHeuristic(int size) : size(size) {}

int MisplacedTileHeuristic::getHeuristic(const std::vector<int>& state) {
    int h = 0;
    for (int i = 0; i < state.size(); i++) {
        int val = state[i];
        if (val == 0) {
            continue;
        }
        int row = i / size;
        int col = i % size;
        int rowGoal = (val - 1) / size;
        int colGoal = (val - 1) % size;

        if (row != rowGoal || col != colGoal) {
            h++;
        }
    }
    return h;
}

int MisplacedTileHeuristic::updateHeuristic(int oldH, int val, int oldIdx, int newIdx) {
    if (val == 0)
        return oldH;

    int oldRow = oldIdx / size;
    int oldCol = oldIdx % size;
    int newRow = newIdx / size;
    int newCol = newIdx % size;

    int goalRow = (val - 1) / size;
    int goalCol = (val - 1) % size;

    bool wasMisplaced = (oldRow != goalRow || oldCol != goalCol);
    bool nowMisplaced = (newRow != goalRow || newCol != goalCol);

    if (wasMisplaced && !nowMisplaced) {
        return oldH - 1;
    } else if (!wasMisplaced && nowMisplaced) {
        return oldH + 1;
    } else {
        return oldH;
    }
}

// ManhattanHeuristic implementation
ManhattanHeuristic::ManhattanHeuristic(int size) : size(size) {}

int ManhattanHeuristic::getHeuristic(const std::vector<int>& state) {
    int h = 0;
    for (int i = 0; i < state.size(); i++) {
        int val = state[i];
        if (val != 0) {
            h += dist(i, val);
        }
    }
    return h;
}

int ManhattanHeuristic::updateHeuristic(int oldH, int val, int oldIdx, int newIdx) {
    int oldDist = dist(oldIdx, val);
    int newDist = dist(newIdx, val);
    return oldH - oldDist + newDist;
}

int ManhattanHeuristic::dist(int idx, int val) {
    if (val == 0)
        return 0;
    int row = idx / size;
    int col = idx % size;
    int goalRow = (val - 1) / size;
    int goalCol = (val - 1) % size;
    return std::abs(row - goalRow) + std::abs(col - goalCol);
}

// HeuristicFactory implementation
std::unique_ptr<Heuristic> HeuristicFactory::getHeuristic(const std::string& type, int size) {
    HeuristicType heuristicType = fromString(type);

    switch (heuristicType) {
        case HeuristicType::NO_HEURISTIC:
            return std::make_unique<NoHeuristic>();
        case HeuristicType::MISPLACED_TILE:
            return std::make_unique<MisplacedTileHeuristic>(size);
        case HeuristicType::MANHATTAN:
            return std::make_unique<ManhattanHeuristic>(size);
        default:
            throw std::runtime_error("Invalid heuristic type.");
    }
}

HeuristicType HeuristicFactory::fromString(const std::string& text) {
    std::string lowerText = text;
    std::transform(lowerText.begin(), lowerText.end(), lowerText.begin(), ::tolower);
    
    if (lowerText == "noheuristic") {
        return HeuristicType::NO_HEURISTIC;
    } else if (lowerText == "misplacedtile") {
        return HeuristicType::MISPLACED_TILE;
    } else if (lowerText == "manhattan") {
        return HeuristicType::MANHATTAN;
    }
    
    throw std::invalid_argument("No heuristic type " + text);
}
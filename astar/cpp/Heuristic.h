#ifndef HEURISTIC_H
#define HEURISTIC_H

#include <vector>
#include <string>
#include <memory>

class Heuristic {
public:
    virtual ~Heuristic() = default;
    virtual int getHeuristic(const std::vector<int>& state) = 0;
    virtual int updateHeuristic(int oldH, int val, int oldIdx, int newIdx) = 0;
};

class NoHeuristic : public Heuristic {
public:
    NoHeuristic() {}
    int getHeuristic(const std::vector<int>& state) override;
    int updateHeuristic(int oldH, int val, int oldIdx, int newIdx) override;
};

class MisplacedTileHeuristic : public Heuristic {
private:
    int size;

public:
    MisplacedTileHeuristic(int size);
    int getHeuristic(const std::vector<int>& state) override;
    int updateHeuristic(int oldH, int val, int oldIdx, int newIdx) override;
};

class ManhattanHeuristic : public Heuristic {
private:
    int size;

public:
    ManhattanHeuristic(int size);
    int getHeuristic(const std::vector<int>& state) override;
    int updateHeuristic(int oldH, int val, int oldIdx, int newIdx) override;

private:
    int dist(int idx, int val);
};

enum class HeuristicType {
    NO_HEURISTIC,
    MISPLACED_TILE,
    MANHATTAN
};

class HeuristicFactory {
public:
    static std::unique_ptr<Heuristic> getHeuristic(const std::string& type, int size);
    static HeuristicType fromString(const std::string& text);
};

#endif
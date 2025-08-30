#include "Encoder.h"
#include <stdexcept>

LongEncoder::LongEncoder(int size) : size(size) {
    if (size > 4) {
        throw std::runtime_error("The puzzle size needs to be smaller than or equal to 4 x 4");
    }
}

long LongEncoder::encode(const std::vector<int>& puzzle) {
    if (puzzle.size() != size * size) {
        throw std::runtime_error("Invalid puzzle size");
    }
    long code = 0;
    for (int i = 0; i < size * size; i++) {
        code <<= 4;
        code |= puzzle[i] & 0xF;
    }
    return code;
}

std::vector<int> LongEncoder::decode(long code) {
    std::vector<int> board(size * size);
    for (int i = size * size - 1; i >= 0; i--) {
        board[i] = (int)(code & 0xF);
        code >>= 4;
    }
    return board;
}
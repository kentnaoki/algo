#ifndef ENCODER_H
#define ENCODER_H

#include <vector>

class Encoder {
public:
    virtual ~Encoder() = default;
    virtual long encode(const std::vector<int>& puzzle) = 0;
    virtual std::vector<int> decode(long encodedPuzzle) = 0;
};

class LongEncoder : public Encoder {
private:
    int size;

public:
    LongEncoder(int size);
    long encode(const std::vector<int>& puzzle) override;
    std::vector<int> decode(long code) override;
};

#endif
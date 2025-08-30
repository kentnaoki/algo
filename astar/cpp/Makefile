CXX = g++
CXXFLAGS = -std=c++17 -O2 -Wall -Wextra
TARGET = astar
SOURCES = Astar.cpp Solver.cpp RandomPuzzle.cpp Encoder.cpp Heuristic.cpp
OBJECTS = $(SOURCES:.cpp=.o)
HEADERS = Astar.h Node.h Solver.h RandomPuzzle.h Encoder.h Heuristic.h

$(TARGET): $(OBJECTS)
	$(CXX) $(OBJECTS) -o $(TARGET)

%.o: %.cpp $(HEADERS)
	$(CXX) $(CXXFLAGS) -c $< -o $@

clean:
	rm -f $(OBJECTS) $(TARGET)

.PHONY: clean
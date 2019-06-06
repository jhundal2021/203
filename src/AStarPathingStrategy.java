import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.PriorityQueue;
import java.util.*;

class AStarPathingStrategy implements PathingStrategy{

    private int hdist(Point current, Point end){
        return (Math.abs(end.x - current.x) + Math.abs(end.y - current.y));
    }

    private List<Point> constructPath(List<Point> path, Node current){
        path.add(current.pt);
        if(current.prior == null)
        {
            Collections.reverse(path);
            path.remove(0);
            return path;
        }
        return constructPath(path, current.prior);
    }

    private class Node{
        private int g;
        private int h;
        private int f;
        private Node prior;
        private Point pt;
        public Node(int g, int h, Node prior, Point pt){
            this.g = g; //distance from start node
            this.h = h; //Heuristic distance from end
            this.f = h + g; //g + h
            this.prior = prior; //previous node
            this.pt = pt; //Point associated with node
        }
        public int getG(){return g;}
        public int getF(){return f;}
        public Point getPt(){return pt;}
    }

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> path = new ArrayList<>();
        HashMap<Point,Node> open = new HashMap<>();
        HashMap<Point,Node> closed = new HashMap<>();
        PriorityQueue<Node> points = new PriorityQueue<>(Comparator.comparingInt(Node::getF));

        Node startNode = new Node(0,hdist(start,end), null, start);
        points.add(startNode);
        Node current;

        while (!points.isEmpty()) {
            current = points.remove();
            if (withinReach.test(current.getPt(), end)){
                return constructPath(path, current);
            }

            List<Point> neighbors = potentialNeighbors.apply(current.getPt())
                    .filter(canPassThrough)
                    .filter(p -> !p.equals(start) && !p.equals(end)).collect(Collectors.toList());

            for (Point neighbor: neighbors) {//traversing through the neighbors of current
                if (!closed.containsKey(neighbor)) {
                    int temp = current.getG() + 1;
                    if(!open.containsKey(neighbor)){//if the neighbor isn't in the open list
                        Node nextNode = new Node(current.getG()+1, hdist(neighbor, end), current , neighbor);
                        points.add(nextNode);
                        open.put(neighbor,nextNode);
                    }
                    else{
                        if (temp < open.get(neighbor).getG()) {
                            Node newNode = new Node(temp, hdist(neighbor, end), current, neighbor);
                            points.add(newNode);
                            points.remove(open.get(neighbor));
                            open.replace(neighbor, newNode);
                        }
                    }
                }
                closed.put(current.getPt(),current);
            }
        }
        return path;
    }
}
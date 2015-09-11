package bomberman.pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import bomberman.level.Level;

public class PathFinding {
	private int [][] map = new int[15][9];
	
	ArrayList<Node> openList = new ArrayList<>();
	ArrayList<Node> closeList = new ArrayList<>();
	
	public PathFinding(Level level) 
        {       
            loadMap(level);
	}
	
	public void loadMap(Level level) {
            // Load tiles
            for (int i = 0; i < 15; i++) {
		for (int j = 0; j < 9; j++) {
			map[i][j] = (level.getTile(i, j).solid()) ? 1 : 0;
		}
            }
            // Load bombs
            int bombSize = Level.bomb.size();
            for (int i = 0; i < bombSize; i++) {
                int bombX = Level.bomb.get(i).getX() >> 6;
                int bombY = Level.bomb.get(i).getY() >> 6;
                map[bombX][bombY] = 1;
            }
            
            // 
            int creepSize = Level.creep.size();
            for(int i = 0 ; i<creepSize;i++)
            {
                int creepX = Level.creep.get(i).getX() >> 6;
                int creepY = Level.creep.get(i).getY() >> 6;
                map[creepX][creepY] = 1;
            }
	}
	
	public int calculateH(int startX, int startY, int goalX, int goalY)	{
		return (int) (Math.abs(goalX - startX) + Math.abs(goalY - startY));
	}
	
	public int findNodeWithLeastF(ArrayList<Node> arrList) {
		Node minNode = arrList.get(0);
		int minIndex = 0;
		
		for (int i = 1; i < arrList.size(); i++) {
			if (minNode.f > arrList.get(i).f) {
				minNode = arrList.get(i);
				minIndex = i;
			}
		}
		
		return minIndex;
	}
	
	public int checkList(ArrayList<Node> arrList, Node node) {
		if (arrList.isEmpty())
			return -1;
		
		for (int j = 0; j < arrList.size(); j++) {
			if (arrList.get(j).x == node.x &&
				arrList.get(j).y == node.y &&
				(arrList.get(j).f <node.f))
				return j;
		}
		
		return -1;
	}
	
	public boolean isInsideMap(int x, int y) {
		return (x >= 0 && x <= 14 && y >= 0 && y <= 8);
	}
	
	public ArrayList<Node> doAlgorithmAStar(Level level, int startX, int startY, int goalX, int goalY) {
		loadMap(level);
		
		startX = startX >> 6;
		startY = startY >> 6;
		goalX = (goalX >> 6);
		goalY = (goalY >> 6);
		
		System.out.printf("Player's position: %d %d \n\n", goalY, goalX);
		
		if (startX == goalX && startY == goalY) {
			System.out.println("Da den dich");
			
			return closeList;
		}
		
		openList = new ArrayList<>();
		closeList = new ArrayList<>();
		
		Node startNode = new Node(startX, startY,  
				0, calculateH(startX, startY, goalX, goalY), null);
		openList.add(startNode);
		while (!openList.isEmpty()) {
			Node minNode = openList.get(findNodeWithLeastF(openList));
			openList.remove(findNodeWithLeastF(openList));
			closeList.add(minNode);
			
			Node[] successors = new Node[4];
			
			if (isInsideMap(minNode.x, minNode.y - 1)) {
				if (map[minNode.x][minNode.y - 1] == 0)
					successors[0] = new Node(minNode.x, minNode.y - 1, 
							minNode.g + 1, calculateH(minNode.x, minNode.y, goalX, goalY),
							minNode);
			}
			
			if (isInsideMap(minNode.x + 1, minNode.y)) {
				if (map[minNode.x + 1][minNode.y] == 0)
					successors[1] = new Node(minNode.x + 1, minNode.y, 
							minNode.g + 1, calculateH(minNode.x, minNode.y, goalX, goalY),
							minNode);
			}
			
			if (isInsideMap(minNode.x, minNode.y + 1)) {
				if (map[minNode.x][minNode.y + 1] == 0)
					successors[2] = new Node(minNode.x, minNode.y + 1, 
							minNode.g + 1, calculateH(minNode.x, minNode.y, goalX, goalY),
							minNode);
			}
			
			if (isInsideMap(minNode.x - 1, minNode.y)) {
				if (map[minNode.x - 1][minNode.y] == 0)
					successors[3] = new Node(minNode.x - 1, minNode.y, 
							minNode.g + 1, calculateH(minNode.x, minNode.y, goalX, goalY),
							minNode);
			}
			
			for (int i = 0; i < successors.length; i++) {
				if (successors[i] == null)
					continue;
				
				if (successors[i].x == goalX && successors[i].y == goalY) {
					System.out.println("Found Target.");
					closeList.add(successors[i]);
					return closeList;
				}
				
				if (checkList(openList, successors[i]) != -1)
					continue;
								
				int index = checkList(closeList,  successors[i]);
				
				if (index != -1)
					continue;
				else 
					openList.add(successors[i]);				
			}	
		}
		
		if ((closeList.get(closeList.size() - 1).x != goalX) ||
			(closeList.get(closeList.size() - 1).y != goalY)) {
			closeList.removeAll(closeList);
		}
		return closeList;
	}
	
	public Point nextStep(Node currentNode) {
		while (currentNode.parentNode.parentNode != null) {
			currentNode = currentNode.parentNode;
		}
		return new Point(currentNode.x, currentNode.y);
	}
	
}

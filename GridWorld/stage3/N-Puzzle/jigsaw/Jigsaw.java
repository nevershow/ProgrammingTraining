package jigsaw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/** 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Jigsaw {
	JigsawNode beginJNode;		// 拼图的起始状态节点
	JigsawNode endJNode;		// 拼图的目标状态节点
	JigsawNode currentJNode;	// 拼图的当前状态节点
	private Vector<JigsawNode> openList;	// open表 ：用以保存已发现但未访问的节点
	private Vector<JigsawNode> closeList;	// close表：用以保存已访问的节点
	private Vector<JigsawNode> solutionPath;// 解路径  ：用以保存从起始状态到达目标状态的移动路径中的每一个状态节点
	private boolean isCompleted;	// 完成标记：初始为false;当求解成功时，将该标记至为true
	private int searchedNodesNum;	// 已访问节点数： 用以记录所有访问过的节点的数量
	private static final int maxNodesNum = 25000;  	// 访问节点数大于25000个则认为搜索失败
	/**拼图构造函数
	 * @param bNode - 初始状态节点
	 * @param eNode - 目标状态节点
	 */
	public Jigsaw(JigsawNode bNode, JigsawNode eNode) {
		beginJNode = new JigsawNode(bNode);
		endJNode = new JigsawNode(eNode);
		currentJNode = new JigsawNode(bNode);
		openList = new Vector<JigsawNode>();
		closeList = new Vector<JigsawNode>();
		
		solutionPath = null;
		isCompleted = false;
		searchedNodesNum = 0;
	}

	/**此函数用于打散拼图：将输入的初始状态节点jNode随机移动len步，返回其打散后的状态节点
	 * @param jNode - 初始状态节点
	 * @param len - 随机移动的步数
	 * @return 打散后的状态节点
	 */
	public static JigsawNode scatter(JigsawNode jNode, int len) {
		int randomDirection;
		JigsawNode jigsawNode = new JigsawNode(jNode);
		for (int t = 0; t < len; t += 1) {
			boolean[] movable = jigsawNode.canMove();
			do{randomDirection = (int) (Math.random() * 4);}
			while(!movable[randomDirection]);
			jigsawNode.move(randomDirection);
		}
			jigsawNode.setInitial();
			return jigsawNode;
	}

	/**获取拼图的当前状态节点
	 * @return currentJNode -  拼图的当前状态节点
	 */
	public JigsawNode getCurrentJNode() {
		return currentJNode;
	}

	/**设置拼图的初始状态节点
	 * @param jNode - 拼图的初始状态节点
	 */
	public void setBeginJNode(JigsawNode jNode) {
		beginJNode = jNode;
	}

	/**获取拼图的初始状态节点
	 * @return beginJNode - 拼图的初始状态节点
	 */
	public JigsawNode getBeginJNode() {
		return beginJNode;
	}

	/**设置拼图的目标状态节点
	 * @param jNode - 拼图的目标状态节点
	 */
	public void setEndJNode(JigsawNode jNode) {
		endJNode = jNode;
	}

	/**获取拼图的目标状态节点
	 * @return endJNode - 拼图的目标状态节点
	 */
	public JigsawNode getEndJNode() {
		return endJNode;
	}

	/**获取拼图的求解状态
	 * @return isCompleted - 拼图已解为true；拼图未解为false
	 */
	public boolean isCompleted() {
		return isCompleted;
	}

	/**计算解的路劲
	 * @return 若有解，则将结果保存在solutionPath中，返回true; 若无解，则返回false
	 */
	private boolean calSolutionPath() {
		if (!isCompleted()) {
			return false;
		} else {
			JigsawNode jNode = currentJNode;
			solutionPath = new Vector<JigsawNode>();
			while (jNode != null) {
				solutionPath.add(jNode);
				jNode = jNode.getParent();
			}
			return true;
		}
	}

	/**获取解路径文本
	 * @return 解路径solutionPath的字符串，若有解，则分行记录从初始状态到达目标状态的移动路径中的每一个状态节点；
	 * 若未解或无解，则返回提示信息。
	 */
	public String getSolutionPath() {
		String str = new String();
		str += "Begin->";
		if (isCompleted) {
			for (int i = solutionPath.size() - 1; i >= 0; i--)
				str += solutionPath.elementAt(i) + "->";
			str+="End";
		} else
			str = "Jigsaw Not Completed.";
		return str;
	}

	/**获取访问过的节点数searchedNodesNum
	 * @return 返回所有已访问过的节点总数
	 */
	public int getSearchedNodesNum() {
		return searchedNodesNum;
	}
	
	/**将搜索结果写入文件中，同时显示在控制台
	 * 若搜索失败，则提示问题无解，输出已访问节点数；
	 * 若搜索成功，则输出初始状态beginJnode，目标状态endJNode，已访问节点数searchedNodesNum，路径深度nodeDepth和解路径solutionPath。
	 * @param pw - 文件输出PrintWriter类对象，如果pw为null，则写入到D://Result.txt
	 * @throws IOException
	 */
	public void printResult(PrintWriter pw) throws IOException{
		boolean flag = false;
		if(pw == null){
			pw = new PrintWriter(new FileWriter("Result.txt"));// 将搜索过程写入BFSearchDialog.txt
			flag = true;
		}
		if (isCompleted == true) {
			// 写入文件
			pw.println("Jigsaw Completed");
			pw.println("Begin state:" + getBeginJNode());
			pw.println("End state:" + getEndJNode());
			pw.println("Solution Path: ");
			pw.println(getSolutionPath());
			pw.println("Total number of searched nodes:" + getSearchedNodesNum());
			pw.println("Length of the solution path is:" + getCurrentJNode().getNodeDepth());
			
			// 输出到控制台
			System.out.println("Jigsaw Completed");
			System.out.println("Begin state:" + getBeginJNode());
			System.out.println("End state:" + getEndJNode());
			System.out.println("Solution Path: ");
			System.out.println(getSolutionPath());
			System.out.println("Total number of searched nodes:" + getSearchedNodesNum());
			System.out.println("Length of the solution path is:" + getCurrentJNode().getNodeDepth());
		} 
		else {
			// 写入文件
			pw.println("No solution. Jigsaw Not Completed");
			pw.println("Begin state:" + getBeginJNode());
			pw.println("End state:" + getEndJNode());
			pw.println("Total number of searched nodes:" + getSearchedNodesNum());
			
			// 输出到控制台
			System.out.println("No solution. Jigsaw Not Completed");
			System.out.println("Begin state:" + getBeginJNode());
			System.out.println("End state:" + getEndJNode());
			System.out.println("Total number of searched nodes:" + getSearchedNodesNum());
		}
		if(flag)
			pw.close();
	}

	/**探索所有与jNode邻接(上、下、左、右)且未曾被访问的节点
	 * @param jNode - 要探索的节点
	 * @return 包含所有与jNode邻接且未曾被访问的节点的Vector<JigsawNode>对象
	 */
	private Vector<JigsawNode> findFollowJNodes(JigsawNode jNode) {
		Vector<JigsawNode> followJNodes = new Vector<JigsawNode>();
		JigsawNode tempJNode;
		for(int i = 0; i < 4; i++){
			tempJNode = new JigsawNode(jNode);
			if(tempJNode.move(i) && !closeList.contains(tempJNode) && !openList.contains(tempJNode))
				followJNodes.add(tempJNode);
		}
		return followJNodes;
	}

	/**排序插入openList：按照节点的代价估值（estimatedValue）将节点插入openList中，估值小的靠前。
	 * @param jNode - 要插入的状态节点
	 */
	private void sortedInsertOpenList(JigsawNode jNode) {
		estimateValue(jNode);
		for (int i = 0; i < openList.size(); i++) {
			if (jNode.getEstimatedValue() < openList.elementAt(i).getEstimatedValue()) {
				openList.insertElementAt(jNode, i);
				return;
			}
		}
		openList.add(jNode);
	}
	
	// ****************************************************************
	/**（实验一）广度优先搜索算法，求解指定3*3拼图（8-数码问题）的最优解。
	 * 要求：填充广度优先搜索算法BFSearch()，执行测试脚本RunnerPart1
	 * 主要涉及函数：BFSearch()
	 * 要求函数结束后：1,isCompleted记录了求解完成状态；
	 *                2,closeList记录了所有访问过的节点；
	 *     		      3,searchedNodesNum记录了访问过的节点数；
	 *                4,solutionPath记录了解路径。
	 * @return isCompleted, 搜索成功时为true,失败为false
	 * @throws IOException
	 */
	public boolean BFSearch() throws IOException {
		String filePath = "BFSearchDialog.txt";  // 将搜索过程写入BFSearchDialog.txt
		PrintWriter pw = new PrintWriter(new FileWriter(filePath));
		// *************************************
		openList.add(beginJNode);
		while (!openList.isEmpty()) {
			currentJNode = openList.firstElement();
			if (currentJNode.equals(endJNode)){
				isCompleted = true;
				break;
			}
			
			closeList.add(openList.remove(0));
			searchedNodesNum++;
			
			// 记录并显示搜索过程
			pw.println("Searching.....Number of searched nodes:" + closeList.size() + "   Current state:" + currentJNode.toString());
			System.out.println("Searching.....Number of searched nodes:" + closeList.size() + "   Current state:" + currentJNode.toString());			

			// 寻找所有与currentJNode邻接且未曾被访问的节点，将它们按代价估值从小到大排序插入openList中
			Vector<JigsawNode> followJNodes = findFollowJNodes(currentJNode);
			openList.addAll(followJNodes);
		}
		calSolutionPath();
		// *************************************
		
		printResult(pw);
		pw.close();
		System.out.println("Record into " + filePath);
		return isCompleted;
	}
	
	/**（Demo+实验二）启发式搜索, 求解随机5*5拼图（24-数码问题）访问节点数大于25000个则认为搜索失败。
	 * 主要涉及函数：ASearch()，estimateValue()
	 * 函数结束后：isCompleted记录了求解完成状态；
	 *            closeList记录了所有访问过的节点；
	 *            searchedNodesNum记录了访问过的节点数；
	 *            solutionPath记录了解路径。
	 * @return 搜索成功返回true,失败返回false
	 * @throws IOException
	 */
	public boolean ASearch() throws IOException{
		String filePath = "ASearchDialog.txt";  // 将搜索过程写入ASearchDialog.txt
		PrintWriter pw = new PrintWriter(new FileWriter(filePath));
		
		// 重置求解完成标记为false
		isCompleted = false;           
		
		// (1)将起始节点放入openList中
		sortedInsertOpenList(beginJNode);
		
		// (2) 如果openList为空，或者访问节点数大于maxNodesNum个，则搜索失败，问题无解;否则循环直到求解成功
		while (!openList.isEmpty() && searchedNodesNum <= maxNodesNum) {
			
			// (2-1)访问openList的第一个节点N，置为当前节点currentJNode
			//      若currentJNode为目标节点，则搜索成功，设置完成标记isCompleted为true，计算解路径，退出。
			currentJNode = openList.firstElement();
			if (currentJNode.equals(endJNode)){
				isCompleted = true;
				break;
			}
			
			// (2-2)从openList中删除节点N,并将其放入closeList中，表示以访问节点			
			closeList.add(openList.remove(0));
			searchedNodesNum++;
			
			// 记录并显示搜索过程
			pw.println("Searching.....Number of searched nodes:" + closeList.size() + "   Current state:" + currentJNode.toString());
			System.out.println("Searching.....Number of searched nodes:" + closeList.size() + "   Current state:" + currentJNode.toString());			

			// (2-3)寻找所有与currentJNode邻接且未曾被访问的节点，将它们按代价估值从小到大排序插入openList中
			// 用以存放某一节点的邻接节点
			Vector<JigsawNode> followJNodes = findFollowJNodes(currentJNode);
			for (JigsawNode jNode : followJNodes)
				sortedInsertOpenList(jNode);
		}
		calSolutionPath();
		printResult(pw);	// 记录搜索结果
		pw.close(); 	    // 关闭输出文件
		System.out.println("Record into " + filePath);
		return isCompleted;
	}
	
	/**（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)=s(n)。
	 * s(n)代表后续节点不正确的数码个数
	 * @param jNode - 要计算代价估计值的节点；此函数会改变该节点的estimatedValue属性值。
	 */
	private void estimateValue(JigsawNode jNode) {
		int backwrong = 0;      // 后续节点不正确的数码个数
		int backright = 0;      // 后续节点正确的数码个数
		int wrong = 0;          // 不正确的数码个数
		int right = 0;          // 正确的数码个数
		int manhattan = 0;      // 不正确的数码 Manhattan distance
		double ODis = 0;          // 不正确的数码 Ojilide distance
		int [] state = jNode.getNodesState();
		int dimension = JigsawNode.getDimension();
		int tmpman;
		int max_man = 0;
		for(int index = 1; index <= dimension * dimension; index++) {
			if(index != 25 && state[index] != 0 && state[index + 1] != 0 && state[index] + 1 != state[index + 1])
				backwrong++;

			if (state[index] != 0) {
				if (state[index] != index) {
					wrong++;
					tmpman = getManhattanDIs(state[index] - 1, index - 1, dimension);
					manhattan += tmpman;
					max_man = Math.max(tmpman, max_man);
					ODis += getODis(state[index] - 1, index - 1, dimension);
				}
			}
		}
		
		jNode.setEstimatedValue((int)(4 * wrong + backwrong * 9 + 7 * manhattan + 5.5 * max_man));
//		jNode.setEstimatedValue((int)(3 * wrong + backwrong * 7 + 9 * manhattan + 5 * manhattan));	
//		if (state[25] != 0 && state[25] > 9) {
//			jNode.setEstimatedValue((int)(3 * wrong + backwrong * 7 + 8 * manhattan + 3 * ODis - 1.5 * state[25]));		
//		}
//		else if (state[25] < 10){
//			jNode.setEstimatedValue((int)(3 * wrong + backwrong * 7 + 8 * manhattan + 3 * ODis - 2 * backright - right));
//		}
//		jNode.setEstimatedValue((int)(backwrong * 7 + 8 * manhattan));		
		//jNode.setEstimatedValue((int)(20 * wrong + 40 * manhattan + 10 * ODis + searchedNodesNum));
//		jNode.setEstimatedValue((int)(2.1 * wrong + 3 * backwrong + 5 * manhattan));
//		jNode.setEstimatedValue((int)(2.015 * wrong + 3.1 * backwrong + 5 * manhattan));
//		jNode.setEstimatedValue((int)(3 * wrong + 6 * backwrong + 9.1 * manhattan));
//		jNode.setEstimatedValue((int)(2.015 * wrong + 3.1 * backwrong + 5 * manhattan -  right - backright ));
//		jNode.setEstimatedValue((int)(2 * wrong + backwrong * 3 + 6 * manhattan - right - 2 * backright ));
//		jNode.setEstimatedValue((int)(1 * wrong + backwrong * 6 + 10 * manhattan + ODis));
//		jNode.setEstimatedValue((int)(1.9 * wrong + backwrong + 3.4 * manhattan));
	}
	
	private int getManhattanDIs(int a, int b, int dim) {
		return Math.abs(a / dim - b / dim) + Math.abs(a % dim - b % dim);
	}
	
	private double getODis(int a, int b, int dim) {
		return Math.sqrt((Math.pow((int)(a / dim - b / dim), 2) + Math.pow((int)(a % dim - b % dim), 2)));
	}
}
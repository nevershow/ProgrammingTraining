package Runners;

import java.io.IOException;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;

public class RunnerPart2 {
	/**测试脚本-2
	 * 实验任务二：利用启发式搜索，求解随机5*5拼图（24-数码问题）
	 * 注意：运行前要修改节点维数，将JigsawNode类中的dimension改为5
	 * 要求：不修改脚本内容，程序能够运行，且得出预期结果
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		// 检查节点维数是否为5
		if(JigsawNode.getDimension() != 5) {
			System.out.print("节点维数不正确，请将JigsawNode类的维数dimension改为5");
			return;
		}
		
		// 生成目标状态对象destNode: {25,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,0};
		JigsawNode destNode = new JigsawNode(new int[]{25,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,0});  		

		// 生成随机初始状态对象startNode：将目标状态打散，生成可解的随机初始状态
		JigsawNode startNode = Jigsaw.scatter(destNode, 1000);
//		JigsawNode startNode = new JigsawNode(new int[]{23,2,11,6,4,20,3,7,14,21,9,5,1,17,10,18,19,8,22,13,12,16,24,0,15,23});
//		JigsawNode startNode = new JigsawNode(new int[]{19,8,7,9,23,10,3,19,5,4,14,2,20,11,6,15,22,13,16,0,1,21,12,18,24,17});
//		JigsawNode startNode = new JigsawNode(new int[]{22,14,1,3,8,11,18,13,7,10,23,12,9,22,24,20,21,16,6,19,4,2,0,17,15,5});
//		JigsawNode startNode = new JigsawNode(new int[]{19,1,2,20,18,3,16,12,5,17,10,11,13,15,9,22,23,6,14,0,24,19,7,4,21,8});
//		JigsawNode startNode = new JigsawNode(new int[]{15,11,17,6,15,3,13,14,9,1,24,8,21,10,4,0,7,20,16,5,19,12,18,22,23,2});
//		JigsawNode startNode = new JigsawNode(new int[]{11,9,5,19,15,7,24,21,2,20,4,0,12,10,11,17,1,6,18,13,8,16,23,22,14,3});
//		JigsawNode startNode = new JigsawNode(new int[]{18,24,3,21,17,5,9,7,23,18,4,16,14,13,12,11,1,20,0,2,8,22,6,15,10,19});
		// 生成jigsaw对象：设置初始状态节点startNode和目标状态节点destNode
		Jigsaw jigsaw = new Jigsaw(startNode, destNode);

		// 执行启发式搜索示例算法
		jigsaw.ASearch();
	}

}

package classification.cart;

public class Client {
	public static void main(String[] args){
		String filePath = Client.class.getResource("input.txt").getPath();
		
		CARTTool tool = new CARTTool(filePath);
		
		tool.startBuildingTree();
	}
}

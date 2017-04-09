import imagereader.Runner;

public class ImageReaderRunner {
	private ImageReaderRunner() {}
	public static void main(String[] args) {
		Runner.run(new ImplementImageIo(), new ImplementImageProcessor());
	}
}

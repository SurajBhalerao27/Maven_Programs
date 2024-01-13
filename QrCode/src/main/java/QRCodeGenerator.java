package main.java;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

// Custom exception for missing URL
class MissingURLException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingURLException(String message) {
		super(message);
	}
}

// Custom exception for missing path
class MissingPathException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingPathException(String message) {
		super(message);
	}
}

// Custom exception for missing QR code image path
class MissingQRCodePathException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingQRCodePathException(String message) {
		super(message);
	}
}

public class QRCodeGenerator {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			try {
				System.out.print("Enter a Google Drive link (or 'q' to exit): ");
				String input = scanner.nextLine();

				if (input.equalsIgnoreCase("q")) {
					System.out.println("Exiting program by user pressing q...");
					break; // Exit the loop if 'q' is entered
				}

				if (input.isEmpty()) {
					throw new MissingURLException("URL is missing. Please provide a valid Google Drive link.");
				}

				System.out.print(
						"Enter the path to store the QR code (or press Enter to use the default path 'qrcodes'): ");
				String outputPath = scanner.nextLine();

				if (outputPath.isEmpty()) {
					outputPath = "qrcodes"; // Use a default path if the user doesn't provide one
				}

				if (outputPath == null || outputPath.isEmpty()) {
					throw new MissingQRCodePathException(
							"Path to store the QR code image is missing. Please provide a valid path.");
				}

				System.out.print(
						"Enter the name of the QR code image file (without extension, or press Enter for a default name): ");
				String fileName = scanner.nextLine();

				if (fileName.isEmpty()) {
					fileName = System.currentTimeMillis() + ""; // Use a default name based on the current timestamp
				}

				generateAndAccessQRCode(input, outputPath, fileName);
			} catch (MissingURLException | MissingPathException | MissingQRCodePathException e) {
				System.err.println("Error: " + e.getMessage());
			} catch (Exception e) {
				System.err.println("An error occurred: " + e.getMessage());
			}
		}

		scanner.close();
	}

	public static void generateAndAccessQRCode(String googleDriveLink, String outputPath, String fileName)
			throws Exception {
		int width = 300; // Width of the QR code image
		int height = 300; // Height of the QR code image
		String fileType = "png"; // Type of the QR code image

		Map<EncodeHintType, Object> hintMap = new HashMap<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // Error correction level

		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(googleDriveLink, BarcodeFormat.QR_CODE, width, height, hintMap);

			// Create a BufferedImage from the BitMatrix
			int matrixWidth = bitMatrix.getWidth();
			int matrixHeight = bitMatrix.getHeight();
			BufferedImage image = new BufferedImage(matrixWidth, matrixHeight, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < matrixWidth; x++) {
				for (int y = 0; y < matrixHeight; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
				}
			}

			// Generate the full file path including the file name and extension
			String filePath = outputPath + File.separator + fileName + "." + fileType;

			// Create the directories if they don't exist
			File outputDirectory = new File(outputPath);
			if (!outputDirectory.exists()) {
				outputDirectory.mkdirs();
			}

			// Save the image to the specified file path
			File qrCodeFile = new File(filePath);
			ImageIO.write(image, fileType, qrCodeFile);

			System.out.println("QR code generated successfully at: " + filePath);
			System.out.println("Access link: " + googleDriveLink);
			openQRCodeFile(filePath);
		} catch (WriterException | IOException e) {
			throw new Exception("Failed to generate QR code: " + e.getMessage());
		}
	}

	public static void openQRCodeFile(String filePath) {
		try {
			File file = new File(filePath);
			if (Desktop.isDesktopSupported() && file.exists()) {
				Desktop.getDesktop().open(file);
			} else {
				System.err.println("Unable to open QR code file.");
			}
		} catch (IOException e) {
			System.err.println("Failed to open QR code file: " + e.getMessage());
		}
	}
}
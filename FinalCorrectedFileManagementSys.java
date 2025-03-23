import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.zip.*;

public class FinalCorrectedFileManagementSys {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            handleChoice(choice);
        }
    }

    public static void displayMenu() {
        System.out.println("\n=============================================");
        System.out.println("          Welcome to File Management System");
        System.out.println("=============================================");
        System.out.println("1. Create File");
        System.out.println("2. Edit File");
        System.out.println("3. Delete File");
        System.out.println("4. View File");
        System.out.println("5. List All Files");
        System.out.println("6. File Details");
        System.out.println("7. Search File");
        System.out.println("8. Create Directory");
        System.out.println("9. Delete Directory");
        System.out.println("10. Move File");
        System.out.println("11. Copy File");
        System.out.println("12. Rename File");
        System.out.println("13. Write to File Without Overwriting");
        System.out.println("14. Append to File");
        System.out.println("15. Display Directory Structure");
        System.out.println("16. Check File Permissions");
        System.out.println("17. Compress File");
        System.out.println("18. Decompress File");
        System.out.println("19. Encrypt File");
        System.out.println("20. Decrypt File");
        System.out.println("21. Split File");
        System.out.println("22. Merge Files");
        System.out.println("23. Calculate File Checksum");
        System.out.println("24. Exit");
        System.out.print("\nPlease enter your choice (1-24): ");
    }

    public static int getUserChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("\n❗ Invalid input. Please enter a number between 1 and 24.");
            return -1;
        }
    }

    public static void handleChoice(int choice) throws IOException {
        switch (choice) {
            case 1 -> createFile();
            case 2 -> editFile();
            case 3 -> deleteFile();
            case 4 -> viewFile();
            case 5 -> listFiles();
            case 6 -> fileDetails();
            case 7 -> searchFile();
            case 8 -> createDirectory();
            case 9 -> deleteDirectory();
            case 10 -> moveFile();
            case 11 -> copyFile();
            case 12 -> renameFile();
            case 13 -> writeWithoutOverwrite();
            case 14 -> appendToFile();
            case 15 -> displayDirectoryStructure();
            case 16 -> checkFilePermissions();
            case 17 -> compressFile();
            case 18 -> decompressFile();
            case 19 -> encryptFile();
            case 20 -> decryptFile();
            case 21 -> splitFile();
            case 22 -> mergeFiles();
            case 23 -> calculateChecksum();
            case 24 -> exitApplication();
            default -> System.out.println("\n⚠️ Invalid choice. Please enter a valid option.");
        }
    }

    public static void exitApplication() {
        System.out.println("\nThank you for using the File Management System. Goodbye!");
        System.exit(0);
    }

    public static void createFile() {
        try {
            System.out.print("\nEnter the file name (with extension): ");
            String fileName = scanner.nextLine();
            File file = new File(fileName);

            if (file.createNewFile()) {
                System.out.println("\n✅ File created successfully: " + file.getName());
            } else {
                System.out.println("\n⚠️ File already exists.");
            }
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to create the file.");
            e.printStackTrace();
        }
    }

    public static void editFile() {
        try {
            System.out.print("\nEnter the file name to edit: ");
            String fileName = scanner.nextLine();
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("\n⚠️ File does not exist.");
                return;
            }

            System.out.print("Enter content to write: ");
            String content = scanner.nextLine();

            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            System.out.println("\n✅ File edited successfully.");
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to edit the file.");
            e.printStackTrace();
        }
    }

    public static void deleteFile() {
        System.out.print("\nEnter the file name to delete: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        if (file.delete()) {
            System.out.println("\n✅ File deleted successfully.");
        } else {
            System.out.println("\n⚠️ Failed to delete the file. Ensure it exists.");
        }
    }

    public static void deleteDirectory() {
        System.out.print("\nEnter directory path to delete: ");
        String dirPath = scanner.nextLine();
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("\n⚠️ Directory does not exist or is not a directory.");
            return;
        }

        if (deleteDirectoryRecursively(dir)) {
            System.out.println("\n✅ Directory deleted successfully.");
        } else {
            System.out.println("\n❗ Failed to delete directory. Ensure all files are removable.");
        }
    }

    public static boolean deleteDirectoryRecursively(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectoryRecursively(file);
                } else {
                    file.delete();
                }
            }
        }
        return dir.delete();
    }

    public static void moveFile() {
        System.out.print("\nEnter the source file path: ");
        String sourcePath = scanner.nextLine();
        System.out.print("Enter the destination file path: ");
        String destPath = scanner.nextLine();

        File sourceFile = new File(sourcePath);
        File destFile = new File(destPath);

        if (!sourceFile.exists()) {
            System.out.println("\n⚠️ Source file does not exist.");
            return;
        }

        try {
            Files.move(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("\n✅ File moved successfully.");
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to move the file.");
            e.printStackTrace();
        }
    }

    public static void copyFile() {
        System.out.print("\nEnter the source file path: ");
        String sourcePath = scanner.nextLine();
        System.out.print("Enter the destination file path: ");
        String destPath = scanner.nextLine();

        File sourceFile = new File(sourcePath);
        File destFile = new File(destPath);

        if (!sourceFile.exists()) {
            System.out.println("\n⚠️ Source file does not exist.");
            return;
        }

        try {
            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("\n✅ File copied successfully.");
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to copy the file.");
            e.printStackTrace();
        }
    }

    public static void renameFile() {
        System.out.print("\nEnter the current file path: ");
        String currentPath = scanner.nextLine();
        System.out.print("Enter the new file name (with extension): ");
        String newName = scanner.nextLine();

        File file = new File(currentPath);
        File newFile = new File(file.getParent(), newName);

        if (!file.exists()) {
            System.out.println("\n⚠️ File does not exist.");
            return;
        }

        if (file.renameTo(newFile)) {
            System.out.println("\n✅ File renamed successfully.");
        } else {
            System.out.println("\n❗ Error: Failed to rename the file.");
        }
    }

    public static void listFiles() {
        System.out.print("\nEnter the directory path to list files: ");
        String dirPath = scanner.nextLine();
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("\n⚠️ Directory does not exist or is not a directory.");
            return;
        }

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("\n📂 The directory is empty.");
            return;
        }

        System.out.println("\n📂 Files in directory:");
        for (File file : files) {
            System.out.println((file.isDirectory() ? "[DIR] " : "[FILE] ") + file.getName());
        }
    }

    public static void fileDetails() {
        System.out.print("\nEnter the file name to get details: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("\n⚠️ File does not exist.");
            return;
        }

        System.out.println("\n📊 File Details:");
        System.out.println("Name: " + file.getName());
        System.out.println("Path: " + file.getAbsolutePath());
        System.out.println("Size: " + file.length() + " bytes");
        System.out.println("Readable: " + file.canRead());
        System.out.println("Writable: " + file.canWrite());
        System.out.println("Executable: " + file.canExecute());
        System.out.println("Last Modified: " + new Date(file.lastModified()));
    }

    public static void searchFile() {
        System.out.print("\nEnter directory path to search in: ");
        String dirPath = scanner.nextLine();
        System.out.print("Enter file name to search for: ");
        String fileName = scanner.nextLine();

        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("\n⚠️ Directory does not exist or is not a directory.");
            return;
        }

        boolean found = searchRecursive(dir, fileName);
        if (!found) {
            System.out.println("\n❗ File not found.");
        }
    }

    public static boolean searchRecursive(File dir, String fileName) {
        File[] files = dir.listFiles();
        if (files == null) return false;

        for (File file : files) {
            if (file.isDirectory()) {
                if (searchRecursive(file, fileName)) return true;
            } else if (file.getName().equalsIgnoreCase(fileName)) {
                System.out.println("\n✅ File Found: " + file.getAbsolutePath());
                return true;
            }
        }
        return false;
    }

    public static void createDirectory() {
        System.out.print("\nEnter the directory path to create: ");
        String dirPath = scanner.nextLine();
        File dir = new File(dirPath);

        if (dir.exists()) {
            System.out.println("\n⚠️ Directory already exists.");
        } else if (dir.mkdirs()) {
            System.out.println("\n✅ Directory created successfully: " + dir.getAbsolutePath());
        } else {
            System.out.println("\n❗ Error: Failed to create the directory.");
        }
    }

    public static void displayDirectoryStructure() {
        System.out.print("\nEnter the directory path: ");
        String dirPath = scanner.nextLine();
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("\n⚠️ Directory does not exist or is not a directory.");
            return;
        }

        System.out.println("\n📁 Directory Structure:");
        printDirectoryTree(dir, 0);
    }

    public static void printDirectoryTree(File dir, int level) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            System.out.println((file.isDirectory() ? "[DIR] " : "[FILE] ") + file.getName());
            if (file.isDirectory()) {
                printDirectoryTree(file, level + 1);
            }
        }
    }

    public static void checkFilePermissions() {
        System.out.print("\nEnter the file path to check permissions: ");
        String filePath = scanner.nextLine();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("\n⚠️ File does not exist.");
            return;
        }

        System.out.println("\n📜 File Permissions:");
        System.out.println("Readable: " + file.canRead());
        System.out.println("Writable: " + file.canWrite());
        System.out.println("Executable: " + file.canExecute());
    }

    public static void writeWithoutOverwrite() {
        try {
            System.out.print("\nEnter the file name: ");
            String fileName = scanner.nextLine();
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("\n⚠️ File does not exist.");
                return;
            }

            System.out.print("Enter content to write (without overwriting): ");
            String content = scanner.nextLine();

            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(content + "\n");
                System.out.println("\n✅ Content added to file without overwriting.");
            }
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to write to the file.");
            e.printStackTrace();
        }
    }

    public static void compressFile() {
        System.out.print("\nEnter the file path to compress: ");
        String filePath = scanner.nextLine();
        File inputFile = new File(filePath);

        if (!inputFile.exists()) {
            System.out.println("\n⚠️ File does not exist.");
            return;
        }

        String compressedFileName = filePath + ".gz";
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(compressedFileName);
             GZIPOutputStream gzipOut = new GZIPOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                gzipOut.write(buffer, 0, bytesRead);
            }
            System.out.println("\n✅ File compressed successfully: " + compressedFileName);
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to compress the file.");
            e.printStackTrace();
        }
    }

    public static void decompressFile() {
        System.out.print("\nEnter the compressed (.gz) file path: ");
        String compressedFilePath = scanner.nextLine();
        File compressedFile = new File(compressedFilePath);

        if (!compressedFile.exists()) {
            System.out.println("\n⚠️ Compressed file does not exist.");
            return;
        }

        String outputFilePath = compressedFilePath.replace(".gz", "");
        try (FileInputStream fis = new FileInputStream(compressedFile);
             GZIPInputStream gzipIn = new GZIPInputStream(fis);
             FileOutputStream fos = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gzipIn.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.println("\n✅ File decompressed successfully: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to decompress the file.");
            e.printStackTrace();
        }
    }

    public static void encryptFile() {
        System.out.print("\nEnter the file path to encrypt: ");
        String filePath = scanner.nextLine();
        System.out.print("Enter encryption key (single character): ");
        char key = scanner.nextLine().charAt(0);

        processFile(filePath, filePath + ".enc", key);
        System.out.println("\n✅ File encrypted successfully.");
    }

    public static void processFile(String inputPath, String outputPath, char key) {
        try (FileInputStream fis = new FileInputStream(inputPath);
             FileOutputStream fos = new FileOutputStream(outputPath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    buffer[i] ^= key; // Simple XOR encryption/decryption
                }
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to process the file.");
            e.printStackTrace();
        }
    }

    public static void decryptFile() {
        System.out.print("\nEnter the encrypted file path: ");
        String encryptedPath = scanner.nextLine();
        System.out.print("Enter decryption key (single character): ");
        char key = scanner.nextLine().charAt(0);

        processFile(encryptedPath, encryptedPath.replace(".enc", ".dec"), key);
        System.out.println("\n✅ File decrypted successfully.");
    }

    public static void splitFile() {
        System.out.print("\nEnter the file path to split: ");
        String filePath = scanner.nextLine();
        System.out.print("Enter the number of parts: ");
        int parts = scanner.nextInt();
        scanner.nextLine();

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("\n⚠️ File does not exist.");
            return;
        }

        long fileSize = file.length();
        long partSize = fileSize / parts;

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            for (int i = 1; i <= parts; i++) {
                String partName = filePath + ".part" + i;
                try (FileOutputStream fos = new FileOutputStream(partName)) {
                    long bytesRemaining = partSize;
                    int bytesRead;
                    while (bytesRemaining > 0 && (bytesRead = fis.read(buffer)) != -1) {
                        int bytesToWrite = (int) Math.min(bytesRead, bytesRemaining);
                        fos.write(buffer, 0, bytesToWrite);
                        bytesRemaining -= bytesToWrite;
                    }
                }
                System.out.println("\n✅ Created part: " + partName);
            }
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to split the file.");
            e.printStackTrace();
        }
    }

    public static void mergeFiles() {
        System.out.print("\nEnter the output file path for the merged file: ");
        String outputPath = scanner.nextLine();

        System.out.print("Enter number of parts to merge: ");
        int parts = scanner.nextInt();
        scanner.nextLine();

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            for (int i = 1; i <= parts; i++) {
                String partPath = outputPath + ".part" + i;
                File partFile = new File(partPath);

                if (!partFile.exists()) {
                    System.out.println("\n⚠️ Part " + i + " not found.");
                    return;
                }

                try (FileInputStream fis = new FileInputStream(partFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
            }
            System.out.println("\n✅ Files merged successfully.");
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to merge files.");
            e.printStackTrace();
        }
    }

    public static void calculateChecksum() {
        System.out.print("\nEnter the file path to calculate checksum: ");
        String filePath = scanner.nextLine();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("\n⚠️ File does not exist.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            System.out.println("\n✅ File Checksum (SHA-256): " + sb.toString());
        } catch (Exception e) {
            System.out.println("\n❗ Error: Unable to calculate checksum.");
            e.printStackTrace();
        }
    }

    public static void viewFile() {
        try {
            System.out.print("\nEnter the file name to view: ");
            String fileName = scanner.nextLine();
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("\n⚠️ File does not exist.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            System.out.println("\n📄 File Contents:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to read the file.");
            e.printStackTrace();
        }
    }

    public static void appendToFile() {
        System.out.print("\nEnter the file path to append to: ");
        String filePath = scanner.nextLine();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("\n⚠️ File does not exist.");
            return;
        }

        try {
            System.out.print("Enter content to append: ");
            String content = scanner.nextLine();
            FileWriter writer = new FileWriter(file, true);
            writer.write("\n" + content);
            writer.close();
            System.out.println("\n✅ Content appended to file successfully.");
        } catch (IOException e) {
            System.out.println("\n❗ Error: Unable to append to the file.");
            e.printStackTrace();
        }
    }
}
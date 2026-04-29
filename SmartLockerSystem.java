import java.time.LocalDateTime;
import java.util.Scanner;

/* =========================================
   ENUMS
========================================= */

enum LockerSize {
    SMALL,
    MEDIUM,
    LARGE
}

enum LockerStatus {
    FREE,
    OCCUPIED
}

enum PackageStatus {
    WAITING,
    DELIVERED,
    EXPIRED
}

/* =========================================
   USER BASE CLASS
========================================= */

class User {
    private final int userId;
    private final String name;
    private final String phone;

    public User(int userId, String name, String phone) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void showUserDetails() {
        System.out.println("User ID : " + userId);
        System.out.println("Name    : " + name);
        System.out.println("Phone   : " + phone);
    }
}

/* =========================================
   CUSTOMER
========================================= */

class Customer extends User {
    public Customer(int userId, String name, String phone) {
        super(userId, name, phone);
    }
}

/* =========================================
   DELIVERY AGENT
========================================= */

class DeliveryAgent extends User {
    public DeliveryAgent(int userId, String name, String phone) {
        super(userId, name, phone);
    }
}

/* =========================================
   PACKAGE ITEM
========================================= */

class PackageItem {
    private final String packageId;
    private final Customer customer;
    private final LockerSize size;
    private final int otp;
    private final LocalDateTime deliveryTime;

    private int lockerId;
    private PackageStatus status;

    public PackageItem(String packageId,
                       Customer customer,
                       LockerSize size,
                       int otp) {

        this.packageId = packageId;
        this.customer = customer;
        this.size = size;
        this.otp = otp;
        this.deliveryTime = LocalDateTime.now();
        this.status = PackageStatus.WAITING;
        this.lockerId = -1;
    }

    public String getPackageId() {
        return packageId;
    }

    public int getOtp() {
        return otp;
    }

    public void assignLocker(int lockerId) {
        this.lockerId = lockerId;
    }

    public void markDelivered() {
        this.status = PackageStatus.DELIVERED;
    }

    public void showPackageDetails() {
        System.out.println("Package ID    : " + packageId);
        System.out.println("Customer Name : " + customer.getName());
        System.out.println("Locker ID     : " + lockerId);
        System.out.println("OTP           : " + otp);
        System.out.println("Size          : " + size);
        System.out.println("Status        : " + status);
        System.out.println("Time          : " + deliveryTime);
    }
}

/* =========================================
   LOCKER
========================================= */

class Locker {
    private final int lockerId;
    private final LockerSize size;

    private LockerStatus status;
    private PackageItem currentPackage;

    public Locker(int lockerId, LockerSize size) {
        this.lockerId = lockerId;
        this.size = size;
        this.status = LockerStatus.FREE;
        this.currentPackage = null;
    }

    public boolean isFree() {
        return status == LockerStatus.FREE;
    }

    public void assignPackage(PackageItem packageItem) {
        this.currentPackage = packageItem;
        this.status = LockerStatus.OCCUPIED;
        packageItem.assignLocker(lockerId);
    }

    public void freeLocker() {
        this.currentPackage = null;
        this.status = LockerStatus.FREE;
    }

    public PackageItem getCurrentPackage() {
        return currentPackage;
    }

    public void showLockerDetails() {
        System.out.println("Locker ID : " + lockerId);
        System.out.println("Size      : " + size);
        System.out.println("Status    : " + status);

        if (currentPackage != null) {
            System.out.println("Package   : " + currentPackage.getPackageId());
        }
    }
}

/* =========================================
   MAIN APPLICATION
========================================= */

public class SmartLockerSystem {

    static Scanner sc = new Scanner(System.in);

    static Customer customer;
    static DeliveryAgent agent;
    static Locker locker;
    static PackageItem packageItem;

    public static void main(String[] args) {

        while (true) {

            printMenu();
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> createCustomer();

                case 2 -> createAgent();

                case 3 -> createLocker();

                case 4 -> placePackage();

                case 5 -> collectPackage();

                case 6 -> showAllDetails();

                case 7 -> {
                    System.out.println("Exiting System...");
                    return;
                }

                default -> System.out.println("Invalid Choice.");
            }
        }
    }

    /* =========================================
       MENU
    ========================================= */

    static void printMenu() {
        System.out.println("\n===== SMART LOCKER MENU =====");
        System.out.println("1. Create Customer");
        System.out.println("2. Create Delivery Agent");
        System.out.println("3. Create Locker");
        System.out.println("4. Place Package");
        System.out.println("5. Collect Package");
        System.out.println("6. Show Details");
        System.out.println("7. Exit");
        System.out.print("Enter Choice: ");
    }

    /* =========================================
       METHODS
    ========================================= */

    static void createCustomer() {
        System.out.print("Enter Customer ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        customer = new Customer(id, name, phone);

        System.out.println("Customer Created Successfully.");
    }

    static void createAgent() {
        System.out.print("Enter Agent ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        agent = new DeliveryAgent(id, name, phone);

        System.out.println("Delivery Agent Created.");
    }

    static void createLocker() {
        System.out.print("Enter Locker ID: ");
        int id = sc.nextInt();

        System.out.println("Choose Size:");
        System.out.println("1. SMALL");
        System.out.println("2. MEDIUM");
        System.out.println("3. LARGE");

        int sizeChoice = sc.nextInt();

        LockerSize size = switch (sizeChoice) {
            case 1 -> LockerSize.SMALL;
            case 2 -> LockerSize.MEDIUM;
            default -> LockerSize.LARGE;
        };

        locker = new Locker(id, size);

        System.out.println("Locker Created.");
    }

    static void placePackage() {

        if (customer == null || locker == null) {
            System.out.println("Create Customer and Locker first.");
            return;
        }

        if (!locker.isFree()) {
            System.out.println("Locker already occupied.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter Package ID: ");
        String packageId = sc.nextLine();

        System.out.print("Enter OTP: ");
        int otp = sc.nextInt();

        packageItem = new PackageItem(
                packageId,
                customer,
                LockerSize.MEDIUM,
                otp
        );

        locker.assignPackage(packageItem);

        System.out.println("Package Placed Successfully.");
    }

    static void collectPackage() {

        if (packageItem == null) {
            System.out.println("No package found.");
            return;
        }

        System.out.print("Enter OTP: ");
        int enteredOtp = sc.nextInt();

        if (enteredOtp == packageItem.getOtp()) {

            packageItem.markDelivered();
            locker.freeLocker();

            System.out.println("Package Collected Successfully.");

        } else {
            System.out.println("Invalid OTP.");
        }
    }

    static void showAllDetails() {

        if (customer != null) {
            System.out.println("\n--- CUSTOMER ---");
            customer.showUserDetails();
        }

        if (agent != null) {
            System.out.println("\n--- AGENT ---");
            agent.showUserDetails();
        }

        if (locker != null) {
            System.out.println("\n--- LOCKER ---");
            locker.showLockerDetails();
        }

        if (packageItem != null) {
            System.out.println("\n--- PACKAGE ---");
            packageItem.showPackageDetails();
        }
    }
}
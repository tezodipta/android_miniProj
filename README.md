# Pranamika

Pranamika is an Android application that allows citizens to rate and review local government officials. It is developed as part of a mini-project for an Android app development course.

## Features

- **View Officials:** Displays a list of government officials with their name, department, and designation.
- **Rate and Review:** Users can rate and write reviews for individual officials.
- **View Reviews:** View all reviews for a particular official.
- **Firebase Integration:** Utilizes Firebase Firestore to store and retrieve data.

## Database Structure

### Firestore Collections

1. **`official`**  
   Stores information about government officials.
   - **Fields:**  
     - `name` (string)  
     - `dept` (string)  
     - `designation` (string)  
   - **Document IDs:**  
     - Named as `officialId1`, `officialId2`, etc.

2. **`reviews`**  
   Automatically created when a review is added for an official.  
   - **Fields:**  
     - `officialId` (string) - Links to the official being reviewed.  
     - `rating` (number) - Rating given by the user.  
     - `comment` (string) - Review text.  
     - `timestamp` (timestamp) - Time of the review.

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/pranamika.git
   cd pranamika

2. **Add your Firebase configuration:**
   - Obtain the `google-services.json` file from your Firebase project.
   - Replace the existing placeholder file in the `app/` directory with your `google-services.json`.

3. **Update Firestore Database:**
   - Ensure the database structure matches the descriptions above.
   - Add some sample data to the `official` collection to test the app.

4. **Build and Run the App:**
   - Open the project in Android Studio.
   - Sync the project with Gradle files.
   - Run the app on an emulator or connected device.

## Project Workflow

1. **User Authentication:** Signup/login for users to access the app.
2. **Official Details Page:** Displays a list of officials fetched from Firestore.
3. **Review Page:** View all reviews and overall rating for a particular official.
4. **Add Review:** Submit a new review for an official.

## Notes

- You must configure Firebase properly for the app to function.
- Ensure the `official` collection has data to display a list of officials initially.

## License

This project is developed for academic purposes and is not intended for commercial use.

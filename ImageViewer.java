// Name: Noelle Dacayo
// Date: April 19, 2023
// App Name: Image Viewer
// Description: A simple image viewer app that load and displays an imagine file

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.io.*;

/**
 * ImageViewer
 */
public class ImageViewer {

    static JFrame window;
    static JPanel buttonPanel;
    static GridBagConstraints gridbag;
    static JLabel imageLabel;
    static JScrollPane imageScrollPane;
    static JButton clearButton, colourButton, openButton, zoomToFitButton, originalSizeButton;
    static Image originalImage;

    /**
     * Opens a colour picker gialogue that allows the user to pick a background
     */
    static void colourClick()
    {
        // Select a colour
        Color selectedColour = JColorChooser.showDialog(window, "Choose a Colour: ", Color.WHITE);
        
        // If the chooser selected a colour
        if(selectedColour != null)
            // Update the GUI
            imageLabel.setBackground(selectedColour);
    }

    /**
     * Opens a file chooser dialogue that allows us to pick an image to be displayed
     */
    static void openImage()
    {
        // Creates a file choose with a default folder      "." means same folder
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\icedt\\Pictures");
        // Filter to show only images
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg", "bmp", "gif"));
        
        // Show the file dialogue
        // If approve [open] is clicked
        if(fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION)
        {
            try {
                // Get the path to the file
                File imageFile = fileChooser.getSelectedFile();
                originalImage = ImageIO.read(imageFile);

                // Update  the GUI
                imageLabel.setIcon(new ImageIcon(originalImage));
            } 
            // Incase we can't display the image
            catch (Exception e) {
                JOptionPane.showMessageDialog(window, "Cannot display this image!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }

    /**
     * Resets the image and background colour
     */
    static void clearClick()
    {
        imageLabel.setBackground(Color.WHITE);  // Resets the background back to white
        imageLabel.setIcon(null);
    }

    /**
     * Zooms the image to fit the available space
     */
    static void zoomToFit()
    {
        // If there's no image, then stop (do nothing)
        if(imageLabel.getIcon() == null)
            return;

        // Check the difference in size between the scrollPane and image
        double widthRatio =  (double) imageScrollPane.getWidth() / originalImage.getWidth(null);
        double heightRatio = (double) imageScrollPane.getHeight() / originalImage.getHeight(null);

        // Get the smallest zoom
        double zoom = Math.min(widthRatio, heightRatio);

        // Scale the image to fit the scrollPane width and height
        Image scaledImage = originalImage.getScaledInstance(
            (int) (originalImage.getWidth(null) * zoom), 
            (int) (originalImage.getHeight(null)* zoom), 
            Image.SCALE_SMOOTH);

        // Update the GUI
        imageLabel.setIcon((new ImageIcon(scaledImage)));
    }

    /**
     * Resets the image back to the origianl size
     */
    static void originalSize()
    {
        // If there's no image, then stop (do nothing)
        if(imageLabel.getIcon() == null)
            return;

        // Update the GUI
        imageLabel.setIcon((new ImageIcon(originalImage)));
    }

    public static void main(String[] args) {
        
        window = new JFrame("Image Viewer - Noelle Dacayo");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage(new ImageIcon("blush.png").getImage());
        window.setLayout(new GridBagLayout());
        window.setMinimumSize(new Dimension(640, 480));
        gridbag = new GridBagConstraints();

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // NAME LABEL
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        imageLabel = new JLabel();
        imageLabel.setBackground(Color.WHITE);
        imageLabel.setOpaque(true);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // SCROLL PANE
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        imageScrollPane = new JScrollPane(imageLabel);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // BUTTON PANEL
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        buttonPanel = new JPanel();

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // BUTTON
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        clearButton = new JButton("Clear");
        clearButton.addActionListener(event -> clearClick()); // Calls the clearClick method when clicked
        // -------------------------------------------------------------
        colourButton = new JButton("Background Colour");
        colourButton.addActionListener(event -> colourClick()); // Calls the colourClick method when clicked
        // -------------------------------------------------------------
        openButton = new JButton("Open Image");
        openButton.addActionListener(event -> openImage()); // Calls the openImage method when clicked
        // -------------------------------------------------------------
        zoomToFitButton = new JButton("Zoom to Fit");
        zoomToFitButton.addActionListener(event -> zoomToFit()); // Calls the zoomToFit method when clicked
        // -------------------------------------------------------------
        originalSizeButton = new JButton("Original Size");
        originalSizeButton.addActionListener(event -> originalSize()); // Calls the originalSize method when clicked

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // PLACE PANE
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        window.add(imageScrollPane);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // PLACE BUTTON PANEl
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        window.add(buttonPanel);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // PLACE BUTTONS
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        gridbag.gridy = 0;
        gridbag.fill = GridBagConstraints.BOTH;
        gridbag.weightx = 1; // Take over the available space
        gridbag.weighty = 1;
        window.add(imageScrollPane, gridbag);
        gridbag.weighty = 0;
        gridbag.gridy = 1;
        window.add(buttonPanel, gridbag);
        buttonPanel.add(clearButton);
        buttonPanel.add(colourButton);
        buttonPanel.add(openButton);
        buttonPanel.add(zoomToFitButton);
        buttonPanel.add(originalSizeButton);


        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        window.pack();
        window.setVisible(true);
    }
}
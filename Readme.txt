CSE 6431 
Zuanxu Gong
gong.366@osu.edu

# Objective
Write a simulation for a restaurant. The simulation has four sets of parameters - the number of eaters (diners) that wish to enter the restaurant, the times they approach the restaurant and what they order, the number of tables in the restaurant (there can only be as many eaters as there are tables at a time; other eaters must wait for a free table before placing their order), and the number of cooks in the kitchen that process orders.

# Environment
java version "1.8.0_162"

# Usage (under the folder AOSProject_Zuanxu)
1. Make the file
>> make all

2. Run the file
>> make run

Terminal will show:
Please type your input filename (eg: input1.txt):
input1.txt

(PS: You can also type other your customized input file)

3. Clean classes
>> make clean

# Design
1. Assumption
Four sets of parameters:
- The number of diners that wish to enter the restaurant
- Diners' entry time and their order (Each diner takes 30 minutes to finish eating, and then leave and make the table available immediately.)
- The number of tables in the restaurant (One table one eater; other eaters must wait for a free table before placing their order)
- The number of cooks in the kitchen (Each cook handles one order at a time, uses one food machine at a time)

Three types of food:
- Buckeye Burger: burger machine for 5 minutes
- Brutus Fries: fries machine for 3 minutes
- Coke: soda machine for 1 minute 
(Only one machine of each type in the restaurant)

2. Implementation
- One thread for each arriving diner, which competes for an available table.
- One thread for each cook, all of them active for the entire duration when the restaurant is open. 
- Tables and machines for cooking the food are resources whose use must be coordinated among the threads.

# Input file format
If there are N diners arriving, the input has N + 3 lines. 
number of diners
number of tables
number of cooks 
N other lines each with the following four numbers: a number between 0 and 120 stating when the diner arrived (this number is increasing across lines), the number of burgers ordered (1 or higher), number of order of fries (0 or higher), and whether or not coke was ordered (0 or 1).

# Sample output 
(Besides terminal console output, there will also be a generated output file "Out_inputFileName.txt")
1. project-sample-input-1.txt
Please type your input filename (eg: input1.txt):
input1.txt
00:05 - Diner 1 arrives.
00:05 - Diner 1 is seated at table 1.
00:05 - Cook 2 processes Diner 1's order.
00:05 - Cook 2 uses the Burger Machine.
00:06 - Diner 2 arrives.
00:06 - Diner 2 is seated at table 2.
00:06 - Cook 1 processes Diner 2's order.
00:06 - Cook 1 uses the Soda Machine.
00:07 - Diner 3 arrives.
00:10 - Cook 2 uses the Fries Machine.
00:10 - Cook 1 uses the Burger Machine.
00:13 - Cook 2 uses the Soda Machine.
00:14 - Diner 1's order is ready. Diner 1 starts eating.
00:15 - Cook 1 uses the Burger Machine.
00:20 - Diner 2's order is ready. Diner 2 starts eating.
00:44 - Diner 1 finishes. Diner 1 leaves the restaurant.
00:44 - Diner 3 is seated at table 1.
00:44 - Cook 1 processes Diner 3's order.
00:44 - Cook 1 uses the Burger Machine.
00:49 - Cook 1 uses the Fries Machine.
00:50 - Diner 2 finishes. Diner 2 leaves the restaurant.
00:52 - Cook 1 uses the Fries Machine.
00:55 - Cook 1 uses the Soda Machine.
00:56 - Diner 3's order is ready. Diner 3 starts eating.
01:26 - Diner 3 finishes. Diner 3 leaves the restaurant.
01:26 - The last diner leaves the restaurant.

2. project-sample-input-2.txt
Please type your input filename (eg: input1.txt):
input2.txt
00:05 - Diner 1 arrives.
00:05 - Diner 1 is seated at table 1.
00:05 - Cook 1 processes Diner 1's order.
00:05 - Cook 1 uses the Burger Machine.
00:10 - Cook 1 uses the Fries Machine.
00:10 - Diner 2 arrives.
00:10 - Diner 2 is seated at table 2.
00:10 - Cook 2 processes Diner 2's order.
00:10 - Cook 2 uses the Burger Machine.
00:13 - Cook 1 uses the Soda Machine.
00:14 - Diner 1's order is ready. Diner 1 starts eating.
00:15 - Cook 2 uses the Burger Machine.
00:20 - Cook 2 uses the Soda Machine.
00:21 - Diner 2's order is ready. Diner 2 starts eating.
00:44 - Diner 1 finishes. Diner 1 leaves the restaurant.
00:51 - Diner 2 finishes. Diner 2 leaves the restaurant.
01:00 - Diner 3 arrives.
01:00 - Diner 3 is seated at table 1.
01:00 - Cook 1 processes Diner 3's order.
01:00 - Cook 1 uses the Burger Machine.
01:05 - Cook 1 uses the Fries Machine.
01:08 - Cook 1 uses the Fries Machine.
01:11 - Cook 1 uses the Soda Machine.
01:12 - Diner 3's order is ready. Diner 3 starts eating.
01:42 - Diner 3 finishes. Diner 3 leaves the restaurant.
01:42 - The last diner leaves the restaurant.
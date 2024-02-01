# Taskerlyze - Task Management [WIP]

[//]: # (INTRO GIF HERE)

## Table of Contents

- [Description](#description)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Support](#support)
- [License](#license)
- [Status](#status)

<a name="description"></a>
## Description

A simple and easy accessible task management that will help you track tasks and questions by custom categories. A lightweight desktop program focused on tracking what needs to be tracked while working on your device.

### GUI

Some images for current interface:<br>
<img src="https://github.com/zaytiri/taskerlyze/blob/main/readme-images/1.png" width="200" height="400" />
<img src="https://github.com/zaytiri/taskerlyze/blob/main/readme-images/2.png" width="200" height="400" />
<img src="https://github.com/zaytiri/taskerlyze/blob/main/readme-images/3.png" width="200" height="400" /><br>
<img src="https://github.com/zaytiri/taskerlyze/blob/main/readme-images/4.png" width="200" height="400" />
<img src="https://github.com/zaytiri/taskerlyze/blob/main/readme-images/5.png" width="200" height="400" />
<img src="https://github.com/zaytiri/taskerlyze/blob/main/readme-images/6.png" width="200" height="400" />

<a name="features"></a>
## Features

| Status | Feature                                     |
|:-------|:--------------------------------------------|
| ✅      | Tracks tasks and sub-tasks                  |
| ✅      | Tracks questions and answers                |
| ✅      | Mainly interaction with mouse's right-click |
| ✅      | Week view for tracking                      |
| ✅      | Achievements feature by day                 |
| ✅      | Possibility of custom categories            |
| ✅      | Existence of default Archive                |
| ✅      | Multiple profiles                           |
| ✅      | Profile Management                          |
| ✅      | Custom Settings                             |
| ❌      | Time tracking for Tasks                     |
| ❌      | Repeated Tasks                              |
| ❌      | Automatic Reminders for Tasks and Questions |
| ❌      | Set window always on top                    |
| ❌      | Save/export data                            |
| ❌      | Import saved data                           |
| ❌      | Statistics                                  |
| ❌      | Options added to OS context menu            |
| ❌      | Features with associated shortcut keys      |
| ❌      | Bulk actions for Tasks and Questions        |


_Disclaimer: The order does not necessarily describe the priority of development of each feature._

Any new features are **_very_** welcomed. If you need some feature or you have any suggestion, open an issue and I can analyse if it's feasible.

### Current Features

#### _Achievements_
There's an **Achievements** section on past days. This is a feature directly related to defining what was achieved on each completed task (by using a small description).

There's two ways of settings this value:
- by editing the **Achieved** field when editing a task.
- by having the **Enable Achieved Popup** as true in configurations.
  - it's true by default and it means that when a task is completed, there will be a popup asking to fill out the **achieved** field.

When opened, this section will display every achievement defined that was completed in a particular selected day.

#### _Archive_
The first displayed category with the following icon<br>
<img src="https://github.com/zaytiri/taskerlyze/blob/main/ui/src/main/resources/icons/archive.png" width="50" height="50" /><br>
is the archive.

This means that it could be used for organizing tasks and questions for general purposes. If a category is removed, all associated tasks and questions will also be moved to the Archive.

#### _Multiple profiles and Profile Management_

All the features are also separated by profile (except _Settings_ section), meaning that there's another layer of organization within the program.

* There must be at least one profile active and more than one can be created.
* Any profile can be edited or deleted. By deleting a profile, it means that all data is also going to be removed.
* A default profile can be chosen, meaning that the profile defined as default will be the one displayed first when launching the program.

### Future features
All future features are documented in the above table with a ❌ icon. More can be added with time.

<a name="prerequisites"></a>
## Prerequisites

To run this program you will need the following:
- [JRE Download](https://www.java.com/en/download/)

The JRE is necessary to run .jar executable files. You can download the latest version, currently it's at **1.8.0_401**.

<a name="installation"></a>
## Installation

After having downloaded the .zip file, available on Releases page, you will have 1 file:
- _taskerlyze-vx.x.x.jar_ -> the program to be launched. (vx.x.x being the version number)

After double-clicking the _.jar_ file, the program should be launched as expected. It could take a few seconds to be displayed.

If double-clicking the _.jar_ file does not work, you can try the following workaround:
- Make sure you have JRE installed ([Prerequisites](#prerequisites) section) and followed the necessary steps;
- Use the _**Command Prompt**_,
  - Go to the .jar file folder location using ``cd <path>``;
  - Input the following command ``java -jar taskerlyze-vx.x.x.jar``.

<a name="usage"></a>
## Usage
If the program is launched for the first time, a new window will be displayed for a new profile to be added.

After launching the program, all interactions are done by right-clicking the element you want to make changes to.

When you first start, you will have no tasks or questions, so the first thing you can do is right-click the background and select **Add new category**.
If you also right-click in the '...tasks not found...' message, you can also select **Add new task**.

There's a **Settings** page where you can configure certain aspects of the program.

<a name="support"></a>
## Support

If any issues/problems are encountered, please feel free to open an issue.

<a name="license"></a>
## License

[MIT](https://choosealicense.com/licenses/mit/)

<a name="status"></a>
## Status

Currently, fully maintaining it.
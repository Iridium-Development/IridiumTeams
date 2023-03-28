# IridiumTeams
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/bf425571a86e4691a172e2b61ba40956)](https://www.codacy.com/gh/Iridium-Development/IridiumSkyblock/dashboard)
![GitHub](https://img.shields.io/github/license/Iridium-Development/IridiumTeams?color=479fc0)

## Introduction

Teamwork - It's in the name

IridiumTeams is a plugin designed to offer features for team management, cooperation, and gameplay. Specifically, it houses code for team-specific features like a team bank, joining/leaving a team, missions and rewards, and more.

This isn't a traditional, standalone plugin, you don't install it on your server. Rather, you develop a plugin that extends IridiumTeams, and your plugin will have access to IridiumTeams code.

### Compiling

Clone the repo, and run the [build.gradle.tks](https://github.com/Iridium-Development/IridiumTeams/blob/master/build.gradle.kts) script.

If you need to take a look, here is the [Nexus](https://nexus.iridiumdevelopment.net/#browse/browse:maven-public:com%2Firidium%2FIridiumTeams) repo.

## Development

You may notice when compiling and developing against IridiumTeams that there is some code that isn't located in this repo. That's because IridiumTeams is an extension of IridiumCore.

- [IridiumCore](https://github.com/Iridium-Development/IridiumCore)
  - A library containing Utility methods for Messages Inventory and cross version support.
- [IridiumTeams](https://github.com/Iridium-Development/IridiumTeams)
  - This plugin, which extends IridiumCore, and involves all of the code for team management, including leveling, missions, team members, the bank, etc.

## Support

If you think you've found a bug, please make sure you isolate the issue down to IridiumTeams before posting an issue in our [Issues](https://github.com/Iridium-Development/IridiumTeams/issues) tab. While you're there, please follow our issues guidelines.

If you encounter any issues while using the plugin, feel free to join our support [Discord](https://discord.gg/6HJ73mWE7P).

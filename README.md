# Tic Tac Toe

[![Build Status](https://travis-ci.org/MiltenPlescott/tic-tac-toe.svg?branch=master)](https://travis-ci.org/MiltenPlescott/tic-tac-toe)
[![codecov](https://codecov.io/gh/MiltenPlescott/tic-tac-toe/branch/master/graph/badge.svg)](https://codecov.io/gh/MiltenPlescott/tic-tac-toe)
[![codebeat badge](https://codebeat.co/badges/8311b4cf-e378-4434-a11d-33bed436772a)](https://codebeat.co/projects/github-com-miltenplescott-tic-tac-toe-master)
[![Known Vulnerabilities](https://snyk.io/test/github/MiltenPlescott/tic-tac-toe/badge.svg)](https://snyk.io/test/github/MiltenPlescott/tic-tac-toe)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Installation

### Windows

Clone git repository:
```sh
$ git clone https://github.com/MiltenPlescott/tic-tac-toe
```

Build:
```sh
$ gradlew.bat build
```

Run:
```sh
$ gradlew.bat run
```

Run with arguments:
```sh
$ gradlew.bat run --args="here come the arguments"
```

Run JAR:
```sh
$ java -jar build/libs/tic-tac-toe.jar
```

Run JAR with arguments:
```sh
$ java -jar build/libs/tic-tac-toe.jar here come the arguments
```

Generate and open javadoc:
```sh
$ gradlew.bat javadoc
$ start build/docs/javadoc/index.html
```

### Linux

Clone git repository:
```sh
$ git clone https://github.com/MiltenPlescott/tic-tac-toe
```

Build:
```sh
$ ./gradlew build
```

Run:
```sh
$ ./gradlew run
```

Run with arguments:
```sh
$ ./gradlew run --args="here come the arguments"
```

Run JAR:
```sh
$ java -jar build/libs/tic-tac-toe.jar
```

Run JAR with arguments:
```sh
$ java -jar build/libs/tic-tac-toe.jar here come the arguments
```

Generate and open javadoc:
```sh
$ ./gradlew javadoc
$ xdg-open build/docs/javadoc/index.html
```

## Command line arguments

##### Choosing a symbol, that will represent user's moves:
```sh
[-s|--user-symbol <symbol>]
```
  * Available symbols: X, O
  * Default symbol: X

##### Choosing game difficulty:
```sh
[-d|--difficulty <difficulty name|number>]
```
 * Available difficulty levels: random (1), easy (2), impossible (3)
 * Default difficulty: impossible (3)

##### Choosing who starts the game:
```sh
[-u|--user-starts <boolean>]
```
 * Available boolean: true (1), false (0)
 * Default: true - user goes first

## License

Tic Tac Toe is available under MIT License. See [LICENSE.txt](https://github.com/MiltenPlescott/tic-tac-toe/blob/master/LICENSE.txt) for more information.

SPDX-License-Identifier: MIT

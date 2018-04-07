# scala-nette-form-generator

[![Build Status](https://travis-ci.org/jakub-tucek/scala-nette-form-generator.svg?branch=master)](https://travis-ci.org/jakub-tucek/scala-nette-form-generator)
[![codecov](https://codecov.io/gh/jakub-tucek/scala-nette-form-generator/branch/master/graph/badge.svg)](https://codecov.io/gh/jakub-tucek/scala-nette-form-generator)

Scala nette form generator is experimental project created for FIT CUT Scala course in year 2017/18.

### What does it do?

It is web project that will generate latte and php/nette form definitions based on given MySQL script.

### Structure?

Project is divided in two modules - **web(client)** and **backend**. Both modules share common module
that specifies interface used in communication which is done via RPC.

#### Backend

Backend is written Play framework. BE is responsible for serving index page containing compiled javascript
and handling REST requests.


#### Frontend

Client side is written in scalajs-react, which is library build on top of scalajs, so all scala code
is compiled to javascript.


### How does it work?

Client accepts sql text and sends it to backend. Backend creates AST tree from given SQL and creates
required templates via default template engine. Result is returned to client as response.

### 

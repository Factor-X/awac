# Generators: how-to

## Bundler

**Bundler** is the dependency tool for ruby. It reads the *Gemfile* for dependencies and download them if needed.

Just run:

```sh
$ bundle
```

*Info: the Gemfile in this directory contains a "quick and useful hack" to recursively include all nested 
Gemfiles, so there is **1 command to rule them all !***

## Rake

**Rake** is the ruby make tool. It reads the *Rakefile* for tasks and runs them.

In this example we run the task **surveys** in the namespace **generate**:

```sh
$ rake generate:surveys
```

## "Good-to-know" 's about existing tasks

- **rake generate:surveys** :
    - it reads the config.yaml for configuration and spec XLS files, 
    - performs some validation,
    - then generates source files in eu.factorx.awac.generated for surveys
    - and injects question codes in *eu.factorx.awac.models.code.type.QuestionCode*

# Generators: how-to

## Bundler

**Bundler** is the dependency tool for ruby. It reads the *Gemfile* for dependencies and download them if needed.

Just run

```sh
$ bundle
```

## Rake

**Rake** is the ruby make tool. It reads the *Rakefile* for tasks and runs them.

In this example we run the task **surveys** in the namespace **generate**:

```sh
$ rake generate:surveys
```

## "Good-to-know" 's about existing tasks

- **rake generate:surveys** :
    - it reads the config.yaml
    - then generates source files in eu.factorx.awac.generated for surveys
    - and injects question codes in *eu.factorx.awac.models.code.type.QuestionCode*

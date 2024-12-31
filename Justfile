compile:
    rm -rf build
    javac \
      -d build/javac \
      --module-source-path "./modules/*/src" \
      --module example.processor,example.ann \
      --release 17

    javac \
      -d build/javac \
      -s build/generated \
      --module-path build/javac \
      --processor-module-path build/javac \
      --module-source-path "./modules/*/src" \
      --module example.usage \
      --release 17

run: compile
    java --module-path build/javac -m example.usage/apple.Example
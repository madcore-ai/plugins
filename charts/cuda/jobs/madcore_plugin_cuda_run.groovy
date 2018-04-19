job('madcore.plugin.cuda.run') {
    wrappers { preBuildCleanup() }
    parameters {
        stringParam('APP_NAME', 'cuda', '')
        stringParam('EXAMPLE', 'demo.py', 'Run example from: https://github.com/inducer/pycuda/tree/master/examples')
    }
    steps {
        def command = """#!/bin/bash
    pushd /opt/madcore/bin
        python run_cmd_in_container.py "\$APP_NAME" "\$APP_NAME-plugin" "pycuda_example \$EXAMPLE"
    popd
"""
        shell(command)
    }
}

#!/bin/bash

VER=6

helm delete my-xlswriter --purge
kubectl delete pods,svc,statefulsets,pvc --all --namespace=social
#kubectl delete secret gsec
pushd xlswriter
  helm package . --version 0.1.$VER
popd
helm install --name my-xlswriter local/xlswriter --wait --debug
echo
echo
echo
echo
kubectl get pods,svc,statefulsets,pvc -o wide --namespace=social
#kubectl describe statefulset my-xlswriter-xlswriter --namespace=social
echo
echo
echo
echo
kubectl describe pod my-xlswriter-xlswriter-0 --namespace=social
#kubectl --namespace=social exec -it my-xlswriter-xlswriter-0 -- /bin/bash

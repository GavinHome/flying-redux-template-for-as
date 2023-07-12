import 'package:flying_redux/flying_redux.dart';

import 'action.dart';
import 'state.dart';

buildReducer() {
  return asReducer(
    <Object, Reducer<$nameState>>{
      $nameAction.initAction: _initAction,
      $nameAction.someAction: _someAction,
    },
  );
}

$nameState _initAction($nameState state, Action action) {
  final $nameState newState = state.clone();
  return newState;
}

$nameState _someAction($nameState state, Action action) {
  final $nameState newState = state.clone();
  return newState;
}
